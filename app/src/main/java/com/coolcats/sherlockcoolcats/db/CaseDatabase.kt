package com.coolcats.sherlockcoolcats.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.coolcats.sherlockcoolcats.model.Case
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Case::class], version = 1)
@TypeConverters(Converters::class)
public abstract class CaseDatabase : RoomDatabase() {

    abstract fun caseDao() : CaseDao

    private class CaseDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var caseDao = database.caseDao()

                    //Delete all content here
                    caseDao.deleteAll()

                    //Adding a few cases
                    var case = Case(1,  "A Scandal in Bohemia", LatLng(33.9083264, -84.4693504), false)
                    caseDao.insert(case)

                    //ToDo: Add your own case
                    //case = Case()
                    //caseDao.insert()
                }
            }
        }
        }

    companion object{
        private var INSTANCE : CaseDatabase? = null

        fun getDatabase(context : Context,
                        scope: CoroutineScope) : CaseDatabase{
            return  INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CaseDatabase::class.java,
                    "case_database"
                )
                    .addCallback(CaseDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                instance
            }
        }

    }
}