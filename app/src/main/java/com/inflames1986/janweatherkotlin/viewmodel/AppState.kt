package com.inflames1986.janweatherkotlin.viewmodel

import com.inflames1986.janweatherkotlin.model.entities.Weather

sealed class AppState {

    object Loading:AppState()
    data class Success(val weatherList:List<Weather>):AppState(){
        fun test(){}
    }
    data class Error(val error:Throwable):AppState()
}

