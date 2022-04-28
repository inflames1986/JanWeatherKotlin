package com.inflames1986.janweatherkotlin.model.repository

import com.inflames1986.janweatherkotlin.MyApp
import com.inflames1986.janweatherkotlin.model.entities.City
import com.inflames1986.janweatherkotlin.model.entities.Weather
import com.inflames1986.janweatherkotlin.utils.convertHistoryEntityToWeather
import com.inflames1986.janweatherkotlin.utils.convertWeatherToEntity
import com.inflames1986.janweatherkotlin.viewmodel.DetailsViewModel
import com.inflames1986.janweatherkotlin.viewmodel.HistoryViewModel


class DetailsRepositoryRoomImpl : DetailsRepository, DetailsRepositoryAll,
    DetailsRepositoryAdd {


    override fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback) {
        val list =
            convertHistoryEntityToWeather(MyApp.getHistoryDao().getHistoryForCity(city.name))
        if (list.isEmpty()) {
            callback.onFail() // то и отобразить нечего
        } else {
            callback.onResponse(list.last()) // FIXME hack
        }
    }

    override fun getAllWeatherDetails(callback: HistoryViewModel.CallbackForAll) {
        callback.onResponse(convertHistoryEntityToWeather(MyApp.getHistoryDao().getAll()))
    }

    override fun addWeather(weather: Weather) {
        MyApp.getHistoryDao().insert(convertWeatherToEntity(weather))
    }
}



