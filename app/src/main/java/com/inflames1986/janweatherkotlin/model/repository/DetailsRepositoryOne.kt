package com.inflames1986.janweatherkotlin.model.repository

import com.inflames1986.janweatherkotlin.model.entities.City
import com.inflames1986.janweatherkotlin.viewmodel.DetailsViewModel

interface DetailsRepositoryOne {
    fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback)
}
