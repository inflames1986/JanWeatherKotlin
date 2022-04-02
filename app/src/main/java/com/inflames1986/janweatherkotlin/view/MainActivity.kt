package com.inflames1986.janweatherkotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inflames1986.janweatherkotlin.R
import com.inflames1986.janweatherkotlin.view.main.WeatherListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance())
                .commitNow()
        }
    }
}