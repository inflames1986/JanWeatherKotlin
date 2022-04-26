package com.inflames1986.janweatherkotlin.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.inflames1986.janweatherkotlin.MyApp
import com.inflames1986.janweatherkotlin.R
import com.inflames1986.janweatherkotlin.view.main.WeatherListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance())
                .commit()
        }


            MyApp.getHistoryDao().getAll()

    }
}