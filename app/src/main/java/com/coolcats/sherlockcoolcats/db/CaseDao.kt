package com.coolcats.sherlockcoolcats.db

import androidx.room.*
import androidx.room.Dao
import com.coolcats.sherlockcoolcats.model.Case
import kotlinx.coroutines.flow.Flow

/*
 * These are asynchronous to prevent the queries from blocking the IU
 * The insert and update queries are one-shot write queries
 * The read queries for the filtered version are one-shot queries
 * The main read query id an observable read query that will read data from the database everytime
 * a change is made to emit the new data changes.
 *
 * To observe data changes we used the Flow from kotlinx-coroutines.
 * This produces values one at a time (instead all at once) that can
 * generate values from async operations.
 *
 */
@Dao
interface CaseDao {

    //Find all cases in the table
    @Query("SELECT * FROM case_table")
    fun getAllCases(): Flow<MutableList<Case>>

    //Find all cases that are in open status
    @Query("SELECT * FROM case_table WHERE case_status = 1")
    suspend fun getAllOpenCases(): Flow<MutableList<Case>>

    //Find all cases that are in closed status
    @Query("SELECT * FROM case_table WHERE case_status = 0")
    suspend fun getAllClosedCases(): Flow<MutableList<Case>>

    //Allows you to add a new case to the database
    @Insert
    suspend fun insert(case : Case)

    //Allows you to update the case
    @Update
    suspend fun  updateCase(case : Case)

    //Allows the case_table to be cleared
    @Query("DELETE FROM case_table")
    suspend fun deleteAll()
}