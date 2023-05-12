package com.harneeblayze.weatherapppchallenge.data.remote

import com.squareup.moshi.Json

data class AutoWeatherDto(
     val longitude: Double,
     val latitude: Double,
     val timezone: String,
     val hourly: HourlyDto,
     @field:Json(name = "hourly_units")
     val hourlyUnits: HourlyUnitsDto,
     @field:Json(name = "current_weather")
     val currentWeather: CurrentWeatherDto,
)

data class HourlyDto(
    val time: List<String>,
    @field:Json(name = "temperature_2m")
    val temperatures: List<Float>
)


data class HourlyUnitsDto(
     val time: String,
     @field:Json(name = "temperature_2m")
     val temperature: String
)


data class CurrentWeatherDto(
     val time: String,
     val temperature: Float
)
