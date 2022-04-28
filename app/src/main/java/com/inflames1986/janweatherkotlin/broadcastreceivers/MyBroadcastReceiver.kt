package com.inflames1986.janweatherkotlin.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.inflames1986.janweatherkotlin.utils.KEY_BUNDLE_SERVICE_MESSAGE

class MyBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("@@@","MyBroadcastReceiver onReceive ${intent!!.action}")
        intent?.let {
            val extra = it.getStringExtra(KEY_BUNDLE_SERVICE_MESSAGE)
            Log.d("@@@","MyBroadcastReceiver onReceive $extra")
        }
    }
}