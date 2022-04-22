package com.inflames1986.janweatherkotlin.viewmodel

import com.inflames1986.janweatherkotlin.model.entities.Weather

sealed class DetailsState {
    object Loading:DetailsState()
    data class Success(val weather: Weather):DetailsState()
    data class Error(val error:Throwable):DetailsState()
}
