package com.inflames1986.janweatherkotlin.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make
import com.inflames1986.janweatherkotlin.R
import com.inflames1986.janweatherkotlin.view.main.WeatherListFragment
import kotlinx.android.synthetic.main.fragment_wether_list.*

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

    fun Snackbar.myFun(stringRes: Int) {
        make(
            fragmentWetherListButton,
            getString(stringRes),
            Snackbar.LENGTH_INDEFINITE
        )
            .show()
    }
}