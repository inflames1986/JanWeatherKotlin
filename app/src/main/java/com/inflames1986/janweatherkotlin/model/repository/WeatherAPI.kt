package com.inflames1986.janweatherkotlin.model.repository

import com.inflames1986.janweatherkotlin.dto.WeatherDTO
import com.inflames1986.janweatherkotlin.utils.LAT
import com.inflames1986.janweatherkotlin.utils.LON
import com.inflames1986.janweatherkotlin.utils.YANDEX_API_KEY
import com.inflames1986.janweatherkotlin.utils.YANDEX_ENDPOINT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherAPI {
    @GET(YANDEX_ENDPOINT)
    fun getWeather(
        @Header(YANDEX_API_KEY) apikey: String,
        @Query(LAT) lat: Double,
        @Query(LON) lon: Double
    ): Call<WeatherDTO>
}