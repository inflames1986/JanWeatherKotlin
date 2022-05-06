package com.inflames1986.janweatherkotlin.utils

import com.inflames1986.janweatherkotlin.domain.room.HistoryEntity
import com.inflames1986.janweatherkotlin.dto.FactDTO
import com.inflames1986.janweatherkotlin.dto.WeatherDTO
import com.inflames1986.janweatherkotlin.model.entities.City
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
const val KEY_SP_FILE_NAME_1 = "fileName1"
const val KEY_SP_FILE_NAME_1_KEY_IS_RUSSIAN = "is_russian"
const val DataSetRus = true


fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
    val fact: FactDTO = weatherDTO.factDTO
    return (Weather(getDefaultCity(), fact.temperature, fact.feelsLike, fact.icon))
}

fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Weather> {
    return entityList.map {
        Weather(City(it.city, 0.0, 0.0), it.temperature, it.feelsLike, it.icon)
    }
}

fun convertWeatherToEntity(weather: Weather): HistoryEntity {
    return HistoryEntity(0, weather.city.name, weather.temperature,weather.feelsLike, weather.icon)
}