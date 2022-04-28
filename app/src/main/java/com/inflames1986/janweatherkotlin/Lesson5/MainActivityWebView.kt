package com.inflames1986.janweatherkotlin.Lesson5

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.inflames1986.janweatherkotlin.databinding.MainActivityWebviewBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MainActivityWebView : AppCompatActivity() {


    lateinit var binding: MainActivityWebviewBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnOk.setOnClickListener {
            request(binding.etUrl.text.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun request(urlString: String) {

        val handlerCurrent1 = Handler(Looper.myLooper()!!)
        Thread {
            val url = URL(urlString)
            val httpsURLConnection = (url.openConnection() as HttpsURLConnection).apply {
                requestMethod = "GET"
                readTimeout = 2000
            }
            val bufferedReader = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
            val result = convertBufferToResult(bufferedReader)
            runOnUiThread {
                binding.webView.loadDataWithBaseURL(
                    null,
                    result,
                    "text/html; charset=utf-8",
                    "utf-8",
                    null
                )
                binding.webView.settings.javaScriptEnabled = true
            }
            val handlerMainUI1 = Handler(mainLooper)
            val handlerMainUI2 = Handler(Looper.getMainLooper())
            handlerMainUI1.post {
                binding.webView.loadDataWithBaseURL(
                    null,
                    result,
                    "text/html; charset=utf-8",
                    "utf-8",
                    null
                )
            }
            handlerCurrent1.post {
                binding.webView.loadDataWithBaseURL(
                    null,
                    result,
                    "text/html; charset=utf-8",
                    "utf-8",
                    null
                )
            }

            httpsURLConnection.disconnect()
        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun convertBufferToResult(bufferedReader: BufferedReader): String {
        return bufferedReader.lines().collect(Collectors.joining("\n"))
    }
}