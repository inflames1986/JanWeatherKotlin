package com.inflames1986.janweatherkotlin

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.room.Room
import com.inflames1986.janweatherkotlin.di.appModule
import com.inflames1986.janweatherkotlin.domain.room.HistoryDao
import com.inflames1986.janweatherkotlin.domain.room.MyDB
import com.inflames1986.janweatherkotlin.viewmodel.AppState
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {


    override fun onCreate() {
        super.onCreate()
        appContext = this

        startKoin {
            androidContext(this@MyApp)
            modules(appModule)
        }
    }

    companion object {
        private var db: MyDB? = null
        private var appContext: MyApp? = null
        fun getHistoryDao(): HistoryDao {
            if (db == null) {
                if (appContext != null) {
                    db = Room.databaseBuilder(appContext!!,MyDB::class.java,"test")
                        .allowMainThreadQueries().build()
                } else {
                    throw IllegalStateException("что-то пошло не так, и у нас пустое appContext")
                }
            }
            return db!!.historyDao()
        }
    }
}
