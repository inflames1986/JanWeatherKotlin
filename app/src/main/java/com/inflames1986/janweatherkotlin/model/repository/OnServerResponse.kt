package com.inflames1986.janweatherkotlin.model.repository

fun interface OnServerResponse {
    fun onResponse(weatherDTO: WeatherDTO)
}