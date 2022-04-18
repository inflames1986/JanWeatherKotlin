package com.inflames1986.janweatherkotlin.model.repository

import com.inflames1986.janweatherkotlin.viewmodel.ResponseState

fun interface OnServerResponseListener {
    fun onError(error: ResponseState)
    fun onError() {
        TODO("Not yet implemented")
    }
}