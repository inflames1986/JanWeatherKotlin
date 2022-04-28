package com.inflames1986.janweatherkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inflames1986.janweatherkotlin.model.entities.City
import com.inflames1986.janweatherkotlin.model.entities.Weather
import com.inflames1986.janweatherkotlin.model.repository.DetailsRepository
import com.inflames1986.janweatherkotlin.model.repository.DetailsRepositoryAdd
import com.inflames1986.janweatherkotlin.model.repository.DetailsRepositoryRetrofit2Impl
import com.inflames1986.janweatherkotlin.model.repository.DetailsRepositoryRoomImpl

class DetailsViewModel(
    private val liveData: MutableLiveData<DetailsState> = MutableLiveData(),
    private val repository: DetailsRepository = DetailsRepositoryRetrofit2Impl(),
    private val repositoryAdd: DetailsRepositoryAdd = DetailsRepositoryRoomImpl()
) : ViewModel() {


    fun getLiveData() = liveData

    fun getWeather(city: City) {

        liveData.postValue(DetailsState.Loading)
        repository.getWeatherDetails(city, object : Callback {
            override fun onResponse(weather: Weather) {
                liveData.postValue(DetailsState.Success(weather))
                repositoryAdd.addWeather(weather)
            }

            override fun onFail() {
                liveData.postValue(DetailsState.Error(error = Throwable()))
            }
        })
    }

    interface Callback {
        fun onResponse(weather: Weather)

        fun onFail() {

        }
    }
}
