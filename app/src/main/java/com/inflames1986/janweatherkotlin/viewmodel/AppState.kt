package com.inflames1986.janweatherkotlin.viewmodel

import com.inflames1986.janweatherkotlin.model.entities.Weather

sealed class AppState {

    data class Success(val weatherData: List<Weather>) : AppState()
    data class Error(val Error: Throwable) : AppState()
    object Loading : AppState()

}