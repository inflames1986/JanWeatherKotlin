package com.inflames1986.janweatherkotlin.model.repository

import com.inflames1986.janweatherkotlin.model.entities.Weather

interface Repository {

    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
}