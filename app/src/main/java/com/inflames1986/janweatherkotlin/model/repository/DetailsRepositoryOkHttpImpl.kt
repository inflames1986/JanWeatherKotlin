package com.inflames1986.janweatherkotlin.model.repository

import com.google.gson.Gson
import com.inflames1986.janweatherkotlin.BuildConfig
import com.inflames1986.janweatherkotlin.dto.WeatherDTO
import com.inflames1986.janweatherkotlin.model.entities.City
import com.inflames1986.janweatherkotlin.utils.YANDEX_API_KEY
import com.inflames1986.janweatherkotlin.utils.YANDEX_DOMAIN
import com.inflames1986.janweatherkotlin.utils.YANDEX_ENDPOINT
import com.inflames1986.janweatherkotlin.utils.convertDtoToModel
import com.inflames1986.janweatherkotlin.viewmodel.DetailsViewModel
import okhttp3.OkHttpClient
import okhttp3.Request

class DetailsRepositoryOneOkHttpImpl : DetailsRepository {
    override fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback) {
        val client = OkHttpClient()
        val builder = Request.Builder()
        builder.addHeader(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
        builder.url("$YANDEX_DOMAIN${YANDEX_ENDPOINT}lat=${city.lat}&lon=${city.lon}")
        val request = builder.build()
        val call = client.newCall(request)
        Thread {
            val response = call.execute()
            if (response.isSuccessful) {
                val serverResponse = response.body()!!.string()
                val weatherDTO: WeatherDTO = Gson().fromJson(serverResponse, WeatherDTO::class.java)
                val weather = convertDtoToModel(weatherDTO)
                weather.city = city
                callback.onResponse(weather)
            } else {
                //TODO HW
            }
        }.start()
    }
}