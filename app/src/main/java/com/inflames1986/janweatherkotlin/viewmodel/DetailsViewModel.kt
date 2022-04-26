package com.inflames1986.janweatherkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inflames1986.janweatherkotlin.model.entities.City
import com.inflames1986.janweatherkotlin.model.entities.Weather
import com.inflames1986.janweatherkotlin.model.repository.DetailsRepositoryOne
import com.inflames1986.janweatherkotlin.model.repository.DetailsRepositoryOneRetrofit2Impl

class DetailsViewModel(
    private val liveData: MutableLiveData<DetailsState> = MutableLiveData(),
    private val repositoryOne: DetailsRepositoryOne = DetailsRepositoryOneRetrofit2Impl()
) : ViewModel() {

    fun getLiveData() = liveData

    fun getWeather(city: City) {

        liveData.postValue(DetailsState.Loading)
        repositoryOne.getWeatherDetails(city, object : Callback {
            override fun onResponse(weather: Weather) {
                liveData.postValue(DetailsState.Success(weather))
            }

            override fun onFail() {
                liveData.postValue(DetailsState.Error(error = Throwable()))
            }
        })
    }

    interface Callback {
        fun onResponse(weather: Weather)

        fun onFail() {

            lateinit var myErr: ResponseState


        }
    }
}