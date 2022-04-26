package com.inflames1986.janweatherkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inflames1986.janweatherkotlin.model.entities.Weather
import com.inflames1986.janweatherkotlin.model.repository.DetailsRepositoryRoomImpl

class HistoryViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: DetailsRepositoryRoomImpl = DetailsRepositoryRoomImpl()
) :
    ViewModel() {

    fun getData(): LiveData<AppState> {
        return liveData
    }

    fun getAll(){
        repository.getAllWeatherDetails(object :CallbackForAll{
            override fun onResponse(listWeather: List<Weather>) {
                liveData.postValue(AppState.Success(listWeather))
            }

            override fun onFail() {
                TODO("Not yet implemented")
            }

        })
    }

    interface CallbackForAll {
        fun onResponse(listWeather: List<Weather>)
        // TODO HW Fail
        fun onFail()
    }
}