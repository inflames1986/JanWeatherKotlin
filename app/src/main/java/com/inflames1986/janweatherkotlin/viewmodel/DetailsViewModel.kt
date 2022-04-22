package com.inflames1986.janweatherkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inflames1986.janweatherkotlin.model.entities.City
import com.inflames1986.janweatherkotlin.model.entities.Weather
import com.inflames1986.janweatherkotlin.model.repository.DetailsRepository
import com.inflames1986.janweatherkotlin.model.repository.DetailsRepositoryOneOkHttpImpl
import com.inflames1986.janweatherkotlin.model.repository.DetailsRepositoryRetrofit2Impl

class DetailsViewModel(
    private val liveData: MutableLiveData<DetailsState> = MutableLiveData(),
    //private val repository: DetailsRepository = DetailsRepositoryOneOkHttpImpl() // ---- > используем OkHttp
    private val repository: DetailsRepository = DetailsRepositoryRetrofit2Impl()   // ---- > используем Retrofit2
) : ViewModel() {


    fun getLiveData() = liveData

    fun getWeather(city: City) {

        liveData.postValue(DetailsState.Loading)
        repository.getWeatherDetails(city, object : Callback {
            override fun onResponse(weather: Weather) {
                liveData.postValue(DetailsState.Success(weather))
            }
        })
    }


    interface Callback {
        fun onResponse(weather: Weather)
    }
}
