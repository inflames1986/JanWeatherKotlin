package com.inflames1986.janweatherkotlin.model.repository

import com.google.gson.GsonBuilder
import com.inflames1986.janweatherkotlin.BuildConfig
import com.inflames1986.janweatherkotlin.dto.WeatherDTO
import com.inflames1986.janweatherkotlin.model.entities.City
import com.inflames1986.janweatherkotlin.utils.YANDEX_DOMAIN
import com.inflames1986.janweatherkotlin.utils.convertDtoToModel
import com.inflames1986.janweatherkotlin.viewmodel.DetailsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsRepositoryOneRetrofit2Impl : DetailsRepositoryOne {
    override fun getWeatherDetails(city: City, callbackMy: DetailsViewModel.Callback) {
        val weatherAPI = Retrofit.Builder().apply {
            baseUrl(YANDEX_DOMAIN)
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        }.build().create(WeatherAPI::class.java)

        weatherAPI.getWeather(BuildConfig.WEATHER_API_KEY, city.lat, city.lon)
            .enqueue(object : Callback<WeatherDTO> { // ---- > асинхронно
                override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val weather = convertDtoToModel(it)
                            weather.city = city
                            callbackMy.onResponse(weather)
                        }
                    } else {
                        callbackMy.onFail()
                    }
                }

                override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                    // TODO HW
                    callbackMy.onFail()
                }
            })
    }
}