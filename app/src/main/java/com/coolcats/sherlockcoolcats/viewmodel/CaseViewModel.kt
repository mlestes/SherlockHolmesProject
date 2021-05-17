package com.coolcats.sherlockcoolcats.viewmodel


import androidx.lifecycle.*
import com.coolcats.sherlockcoolcats.db.CaseRepository
import com.coolcats.sherlockcoolcats.model.Cases
import kotlinx.coroutines.launch

class CaseViewModel(private val repository: CaseRepository) : ViewModel() {

    //Using LiveData and caching what allCases returns have several benefits:
    //- We can put an observer on the data (instead of polling for changes)
    // and only update the UI when the data actually changes.
    // -The respository is completely separated from the UI through the ViewModel
    val allCases: LiveData<MutableList<Cases>> = repository.allCases.asLiveData()


    /*
     * Launching a new coroutine to insert the data in a non-blocking manner
     */
    fun insert(cases: Cases) = viewModelScope.launch {

        repository.insert(cases)
    }

    /*
     * Launching a new coroutine to update the data in a non-blocking manner
     */
    fun update(cases: Cases) = viewModelScope.launch {
        repository.update(cases)
    }

}

class CaseViewModelFactory(private val repository: CaseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CaseViewModel::class.java)) {
            return CaseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
