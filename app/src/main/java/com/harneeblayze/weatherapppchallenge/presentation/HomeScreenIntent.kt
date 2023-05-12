package com.harneeblayze.weatherapppchallenge.presentation

sealed class WeatherIntent {
    object LoadWeatherData : WeatherIntent()

    data class DisplayCityName(val cityName: String) : WeatherIntent()

    object CancelWeatherDataPolling : WeatherIntent()
}