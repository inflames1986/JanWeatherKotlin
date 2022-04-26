package com.inflames1986.janweatherkotlin

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.inflames1986.janweatherkotlin.domain.room.HistoryDao
import com.inflames1986.janweatherkotlin.domain.room.MyDB

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object{
        private var db:MyDB?=null
        private var appContext:MyApp?=null
        fun getHistoryDao():HistoryDao{
            if(db==null){
                if(appContext!=null){
                    db = Room.databaseBuilder(appContext!!,MyDB::class.java,"test")
                        .allowMainThreadQueries() // TODO HW а вам нужно придумать что-то другое
                        .addMigrations(migration_1_2)
                        .addMigrations(migration_1_2)
                        .addMigrations(migration_1_2)
                        .build()
                }else{
                    throw IllegalStateException("что-то пошло не так, и у нас пустое appContext")
                }
            }
            return db!!.historyDao()
        }

        private val migration_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE history_table ADD column condition TEXT NOT NULL DEFAULT ''")
            }
        }
    }
}