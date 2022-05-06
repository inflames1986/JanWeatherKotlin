package com.inflames1986.janweatherkotlin.view

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.inflames1986.janweatherkotlin.MyApp
import com.inflames1986.janweatherkotlin.R
import com.inflames1986.janweatherkotlin.historylist.HistoryWeatherListFragment
import com.inflames1986.janweatherkotlin.utils.KEY_SP_FILE_NAME_1
import com.inflames1986.janweatherkotlin.utils.KEY_SP_FILE_NAME_1_KEY_IS_RUSSIAN
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

        Thread {
            MyApp.getHistoryDao().getAll()
        }.start()


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.action_history -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, HistoryWeatherListFragment.newInstance())
                    .addToBackStack("").commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
