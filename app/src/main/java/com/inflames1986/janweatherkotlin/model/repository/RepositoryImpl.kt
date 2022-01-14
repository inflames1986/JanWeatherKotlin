package com.inflames1986.janweatherkotlin.model.repository

import com.inflames1986.janweatherkotlin.model.entities.Weather

class RepositoryImpl: Repository {
    override fun getWeatherFromServer() = Weather ()

    override fun getWeatherFromLocalStorage() = Weather()
}