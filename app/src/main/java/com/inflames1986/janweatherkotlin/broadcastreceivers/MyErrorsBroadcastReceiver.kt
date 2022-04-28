package com.inflames1986.janweatherkotlin.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.inflames1986.janweatherkotlin.utils.KEY_ERROR_SERVICE_MESSAGE

class MyErrorsBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("@@@", "MyErrorsBroadcastReceiver onReceive ${intent!!.action}")
        intent.let {
            val extraError = it.getStringExtra(KEY_ERROR_SERVICE_MESSAGE)
            Log.d("@@@", "MyErrorsBroadcastReceiver onReceive $extraError")
        }
    }
}