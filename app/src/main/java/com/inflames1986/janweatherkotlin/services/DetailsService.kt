package com.inflames1986.janweatherkotlin.services

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.inflames1986.janweatherkotlin.BuildConfig
import com.inflames1986.janweatherkotlin.dto.WeatherDTO
import com.inflames1986.janweatherkotlin.model.repository.OnServerResponse
import com.inflames1986.janweatherkotlin.model.repository.OnServerResponseListener
import com.inflames1986.janweatherkotlin.utils.*
import com.inflames1986.janweatherkotlin.viewmodel.ResponseState
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailsService(
    val name: String = "",

) : IntentService(name) {

    override fun onHandleIntent(intent: Intent?) {
        Log.d("@@@", "work DetailsService")
        intent?.let {
            val lon = it.getDoubleExtra(KEY_BUNDLE_LON, 0.0)
            val lat = it.getDoubleExtra(KEY_BUNDLE_LAT, 0.0)
            Log.d("@@@", "work DetailsService $lat $lon")

            val urlText =
                "$YANDEX_DOMAIN${YANDEX_PATH}lat=$lat&lon=$lon"
            val uri = URL(urlText)

            val urlConnection: HttpsURLConnection =
                (uri.openConnection() as HttpsURLConnection).apply {
                    connectTimeout = 1000
                    readTimeout = 1000
                    addRequestProperty(
                        YANDEX_API_KEY,
                        BuildConfig.WEATHER_API_KEY
                    )
                }

            val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)


            val headers = urlConnection.headerFields
            val responseCode = urlConnection.responseCode
            val responseMessage = urlConnection.responseMessage
            var myErr: String? = " "

            try {

                val serverside = 500..599
                val clientside = 400..499
                val responseOk = 200..299



                when (responseCode) {
                    in serverside -> {
                        myErr = "Ошибка serverside"

                    }
                    in clientside -> {
                        myErr = "Ошибка clientside"

                    }
                    in responseOk -> {

                        myErr = "Успешно"
                    }
                }


            } catch (e: JsonSyntaxException) {
                //TODO обработчик как сделаю сервис + HardMode Андрея если буду успевать
            } finally {
                urlConnection.disconnect()
            }

            val message = Intent(KEY_WAVE_SERVICE_BROADCAST)
            message.putExtra(
                KEY_BUNDLE_SERVICE_BROADCAST_WEATHER, weatherDTO
            )

            val messageErr = Intent(KEY_WAVE_ERROR_BROADCAST)
            messageErr.putExtra(
                KEY_ERROR_MESSAGE, myErr
            )

            //sendBroadcast(message)
            LocalBroadcastManager.getInstance(this).sendBroadcast(message)
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageErr)
        }
    }
}