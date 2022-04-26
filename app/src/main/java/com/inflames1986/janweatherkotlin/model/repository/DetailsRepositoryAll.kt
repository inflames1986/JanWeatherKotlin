package com.inflames1986.janweatherkotlin.model.repository

import com.inflames1986.janweatherkotlin.viewmodel.HistoryViewModel

interface DetailsRepositoryAll {
    fun getAllWeatherDetails(callback: HistoryViewModel.CallbackForAll)
}