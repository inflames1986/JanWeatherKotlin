package com.inflames1986.janweatherkotlin.viewmodel

import com.inflames1986.janweatherkotlin.model.entities.Weather

sealed class ResponseState {
    object Error1:ResponseState()
    data class Error2(val weatherList:List<Weather>):ResponseState(){
        fun test(){}
    }
    data class Error3(val error:Throwable):ResponseState()
}