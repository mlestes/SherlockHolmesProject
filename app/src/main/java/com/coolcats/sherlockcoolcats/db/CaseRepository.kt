package com.coolcats.sherlockcoolcats.db

import androidx.annotation.WorkerThread
import com.coolcats.sherlockcoolcats.model.Cases
import kotlinx.coroutines.flow.Flow

/*
    The CaseRepository class abstracts access to multiple data sources.
    While this is not part of the Architecture Components libraries,
    but is a suggest best practice for code separation and architecture.

    This application will provide a clean API for data access to
    the rest of the application.

    The DAO is passed into the repository constructor as opposed to the
    whole database because it only needs access to the DAO, since the
    DAO contains all the read\write methods for the database.
 */

class CaseRepository(private val caseDao: CaseDao) {

    val allCases : Flow<MutableList<Cases>>get() = caseDao.getAllCases()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(cases: Cases) {
        caseDao.insert(cases)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(cases : Cases){
        caseDao.updateCase(cases)
    }
}