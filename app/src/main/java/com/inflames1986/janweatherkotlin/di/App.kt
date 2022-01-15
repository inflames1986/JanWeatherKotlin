package com.inflames1986.janweatherkotlin.di

import android.app.Application
import com.inflames1986.janweatherkotlin.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}