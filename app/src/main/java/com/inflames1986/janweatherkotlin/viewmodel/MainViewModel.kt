package com.inflames1986.janweatherkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inflames1986.janweatherkotlin.model.repository.Repository
import java.lang.Thread.sleep

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val liveDataToObserve = MutableLiveData<AppState>()

    fun getLiveData(): LiveData<AppState> = liveDataToObserve

    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)

    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)

    fun getWeatherFromRemoteSource() = getDataFromLocalSource(isRussian = true)


    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(
                AppState.Success(
                    if (isRussian) repository.getWeatherFromLocalStorageRus()
                    else repository.getWeatherFromLocalStorageWorld()
                )
            )
        }.start()
    }
}