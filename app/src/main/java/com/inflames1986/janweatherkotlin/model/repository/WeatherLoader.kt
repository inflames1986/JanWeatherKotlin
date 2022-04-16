package com.inflames1986.janweatherkotlin.model.repository

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.inflames1986.janweatherkotlin.utils.YANDEX_API_KEY
import com.inflames1986.janweatherkotlin.utils.YANDEX_DOMAIN
import com.inflames1986.janweatherkotlin.utils.YANDEX_PATH
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class WeatherLoader(
    private val onServerResponseListener: OnServerResponse
) {

    fun loadWeather(lat: Double, lon: Double) {
        val urlText =
            "https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon" //TODO HW выносим https://api.weather.yandex.ru/v2/informers? в Utlis.kt константы
        //val urlText = "http://212.86.114.27/v2/informers?lat=$lat&lon=$lon"
        val uri = URL(urlText)

        Thread {
            val urlConnection: HttpsURLConnection =
                (uri.openConnection() as HttpsURLConnection).apply {
                    connectTimeout = 1000 // set под капотом
                    //val r= readTimeout // get под капотом
                    readTimeout = 1000 // set под капотом
                    addRequestProperty("X-Yandex-API-Key", "ceae3d76-b634-4bfd-8ef5-25a327758ae9")
                }

//val urlConnection: HttpsURLConnection = (uri.openConnection() as HttpsURLConnection).apply { для ленивых

            val headers = urlConnection.headerFields
            val responseCode = urlConnection.responseCode
            val responseMessage = urlConnection.responseMessage


            val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)
            Handler(Looper.getMainLooper()).post {
                onServerResponseListener.onResponse(weatherDTO)
            }

        }.start()
    }
}