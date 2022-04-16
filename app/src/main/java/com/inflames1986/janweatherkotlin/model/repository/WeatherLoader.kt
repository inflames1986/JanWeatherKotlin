package com.inflames1986.janweatherkotlin.model.repository

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.inflames1986.janweatherkotlin.BuildConfig
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
            "$YANDEX_DOMAIN${YANDEX_PATH}lat=$lat&lon=$lon"
        //"$YANDEX_DOMAIN_HARD_MODE${YANDEX_PATH}lat=$lat&lon=$lon"
        val uri = URL(urlText)

        Thread {
            val urlConnection: HttpsURLConnection =
                (uri.openConnection() as HttpsURLConnection).apply {
                    connectTimeout = 1000 // set под капотом
                    readTimeout = 1000 // set под капотом
                    addRequestProperty(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
                }

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