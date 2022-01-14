package com.inflames1986.janweatherkotlin

import com.inflames1986.janweatherkotlin.model.entities.Weather

sealed class AppState {

    data class Success(val weatherData: Weather) : AppState()
    data class Error(val Error: Throwable) : AppState()
    object Loading : AppState()

}