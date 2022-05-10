package com.inflames1986.janweatherkotlin.view.main

import com.inflames1986.janweatherkotlin.model.entities.Weather

interface OnItemListClickListener {
    fun onItemListClick(weather: Weather)
}