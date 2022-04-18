package com.inflames1986.janweatherkotlin.model.repository

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
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
    lateinit var msgResponse: String

    fun loadWeather(lat: Double, lon: Double) {
        val urlText =
            "$YANDEX_DOMAIN${YANDEX_PATH}lat=$lat&lon=$lon"
        //"$YANDEX_DOMAIN_HARD_MODE${YANDEX_PATH}lat=$lat&lon=$lon"
        val uri = URL(urlText)

        Thread {
            val urlConnection: HttpsURLConnection =
                (uri.openConnection() as HttpsURLConnection).apply {
                    connectTimeout = 1000 // set под капотом
                    //val r= readTimeout // get под капотом
                    readTimeout = 1000 // set под капотом
                    addRequestProperty(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
                }
            try {

                //val urlConnection: HttpsURLConnection = (uri.openConnection() as HttpsURLConnection).apply { для ленивых
                Thread.sleep(500)
                val headers = urlConnection.headerFields
                val responseCode = urlConnection.responseCode
                val responseMessage = urlConnection.responseMessage

                val serverside = 500..599
                val clientside = 400..499
                val responseOk = 200..299

                when (responseCode) {
                    in serverside -> {
                        // Toast.makeText(, msgResponse, Toast.LENGTH_LONG).show() //TODO: найти способ получить контекст :(
                    }
                    in clientside -> {
                        //  Toast.makeText(, msgResponse, Toast.LENGTH_LONG).show() //TODO: найти способ получить контекст :(
                    }
                    in responseOk -> {
                        val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                        val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)
                        Handler(Looper.getMainLooper()).post {
                            onServerResponseListener.onResponse(weatherDTO)
                            msgResponse = "Успешно"
                        }
                        // Toast.makeText(, msgResponse, Toast.LENGTH_LONG).show() //TODO: найти способ получить контекст :(
                    }
                }

                // TODO  HW "что-то пошло не так" Snackbar?


            } catch (e: JsonSyntaxException) {

            } finally {
                urlConnection.disconnect()
            }
            // поток закрывается
        }.start()
    }
}