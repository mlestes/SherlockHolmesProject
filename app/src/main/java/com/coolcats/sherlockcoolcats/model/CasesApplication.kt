package com.coolcats.sherlockcoolcats.model

import android.app.Application
import com.coolcats.sherlockcoolcats.db.CaseDatabase
import com.coolcats.sherlockcoolcats.db.CaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/*
 * The CaseApplication class allows us to have only one instance of
 * the database and the repository in the application.
 * This also allows us to retrieve them when they're needed, rather than
 * constructing every time.
 */
class CaseApplication : Application() {

    //No need to cancel this scope as it'll be torn down within the process
    private val applicationScope = CoroutineScope(SupervisorJob())

    //Using by lazy so the database and the repository are
    //only created when they are needed rather than when the application
    //starts
    val database by lazy { CaseDatabase.getDatabase(this, applicationScope) }
    val repository by lazy{ CaseRepository(database.caseDao())}
}