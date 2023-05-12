package com.harneeblayze.weatherapppchallenge.domain.weather

data class Weather(
    val latitude: Double,
    val longitude: Double,
    val hourlyWeather: HourlyWeather,
    val currentWeather: CurrentWeather,
)

data class HourlyWeather(val data: List<WeatherInfo>)
data class CurrentWeather(val temperature: String, val time:String)

data class WeatherInfo(val temperature: String, val time: String, val weatherType: WeatherType)