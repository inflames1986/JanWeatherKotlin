package com.inflames1986.janweatherkotlin.model.repository

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.inflames1986.janweatherkotlin.BuildConfig
import com.inflames1986.janweatherkotlin.utils.YANDEX_API_KEY
import com.inflames1986.janweatherkotlin.utils.YANDEX_DOMAIN
import com.inflames1986.janweatherkotlin.utils.YANDEX_PATH
import com.inflames1986.janweatherkotlin.viewmodel.ResponseState
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class WeatherLoader(
    private val onServerResponseListener: OnServerResponse,
    private val onServerResponse: OnServerResponseListener
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

                lateinit var myErr: ResponseState

                when (responseCode) {
                    in serverside -> {
                        myErr = ResponseState.Error1
                        onServerResponse.onError(myErr)


                    }
                    in clientside -> {
                        myErr = ResponseState.Error2
                        onServerResponse.onError(myErr)

                    }
                    in responseOk -> {
                        val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                        val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)

                        Handler(Looper.getMainLooper()).post {
                            myErr = ResponseState.Error3
                            onServerResponse.onError(myErr)
                            onServerResponseListener.onResponse(weatherDTO)

                        }

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