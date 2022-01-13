package com.inflames1986.janweatherkotlin.model.entities

data class Weather(

    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0
)

fun getDefaultCity() = City("Севастополь", 35.2343462, 37.653246)