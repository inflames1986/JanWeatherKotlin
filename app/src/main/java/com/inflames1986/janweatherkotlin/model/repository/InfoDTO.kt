package com.inflames1986.janweatherkotlin.model.repository

import com.google.gson.annotations.SerializedName


data class InfoDTO(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("url")
    val url: String
)