package com.inflames1986.janweatherkotlin.model.repository

import com.inflames1986.janweatherkotlin.dto.WeatherDTO

fun interface OnServerResponse {
    fun onResponse(weatherDTO: WeatherDTO)
}