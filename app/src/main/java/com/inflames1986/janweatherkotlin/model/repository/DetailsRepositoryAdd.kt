package com.inflames1986.janweatherkotlin.model.repository

import com.inflames1986.janweatherkotlin.model.entities.Weather


interface DetailsRepositoryAdd {
    fun addWeather(weather: Weather)
}