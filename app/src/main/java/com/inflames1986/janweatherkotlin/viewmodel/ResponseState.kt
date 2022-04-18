package com.inflames1986.janweatherkotlin.viewmodel

import com.inflames1986.janweatherkotlin.model.entities.Weather

sealed class ResponseState {
    object Error1:ResponseState()
    object Error2:ResponseState()
    object Error3:ResponseState()
}