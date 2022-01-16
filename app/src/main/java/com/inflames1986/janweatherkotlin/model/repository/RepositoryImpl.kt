package com.inflames1986.janweatherkotlin.model.repository

import com.inflames1986.janweatherkotlin.model.entities.Weather
import com.inflames1986.janweatherkotlin.model.entities.getRussianCities
import com.inflames1986.janweatherkotlin.model.entities.getWorldCities

class RepositoryImpl: Repository {
    override fun getWeatherFromServer(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorageRus(): List<Weather> {
        return getRussianCities()
    }

    override fun getWeatherFromLocalStorageWorld(): List<Weather> {
        return getWorldCities()
    }

}