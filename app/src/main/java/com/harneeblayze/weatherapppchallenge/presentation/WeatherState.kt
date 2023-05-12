package com.harneeblayze.weatherapppchallenge.presentation

import com.harneeblayze.weatherapppchallenge.domain.weather.Weather

data class WeatherState(
    val cityName: String = "",
    val weather: Weather? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
