package com.inflames1986.janweatherkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.inflames1986.janweatherkotlin.model.entities.City
import com.inflames1986.janweatherkotlin.model.entities.Weather
import com.inflames1986.janweatherkotlin.model.repository.DetailsRepository
import com.inflames1986.janweatherkotlin.model.repository.DetailsRepositoryRetrofit2Impl

class DetailsViewModel(
    private val liveData: MutableLiveData<DetailsState> = MutableLiveData(),
    private val repository: DetailsRepository = DetailsRepositoryRetrofit2Impl()
) : ViewModel() {


    fun getLiveData() = liveData

    fun getWeather(city: City) {

        liveData.postValue(DetailsState.Loading)
        repository.getWeatherDetails(city, object : Callback {
            override fun onResponse(weather: Weather) {
                liveData.postValue(DetailsState.Success(weather))
            }

            override fun onFail() {

                //TODO HW liveData.postValue(DetailsState.Error()) ("Not yet implemented")TODO("Not yet implemented")
            }
        })
    }


    interface Callback {
        fun onResponse(weather: Weather)

        fun onFail() //TODO: Доделать в HW
    }
}
