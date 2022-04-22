package com.inflames1986.janweatherkotlin.utils

import com.inflames1986.janweatherkotlin.dto.FactDTO
import com.inflames1986.janweatherkotlin.dto.WeatherDTO
import com.inflames1986.janweatherkotlin.model.entities.Weather
import com.inflames1986.janweatherkotlin.model.entities.getDefaultCity

const val KEY_BUNDLE_WEATHER = "weather"
const val YANDEX_DOMAIN = "https://api.weather.yandex.ru/"
const val YANDEX_DOMAIN_HARD_MODE = "http://212.86.114.27/"
const val YANDEX_PATH = "v2/informers?"
const val YANDEX_API_KEY = "X-Yandex-API-Key"
const val YANDEX_ENDPOINT = "v2/informers?"
const val KEY_BUNDLE_LAT = "lat1"
const val KEY_BUNDLE_LON = "lon1"
const val KEY_BUNDLE_SERVICE_MESSAGE = "kbsm"
const val KEY_BUNDLE_SERVICE_BROADCAST_WEATHER = "kbsbw"
const val KEY_WAVE_SERVICE_BROADCAST = "kwsb"
const val KEY_ERROR_SERVICE_MESSAGE = "kesm"
const val KEY_ERROR_MESSAGE = "kem"
const val KEY_WAVE_ERROR_BROADCAST = "kweb"
const val LAT = "lat"
const val LON = "lon"


fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
    val fact: FactDTO = weatherDTO.factDTO
    return (Weather(getDefaultCity(), fact.temperature, fact.feelsLike))
}