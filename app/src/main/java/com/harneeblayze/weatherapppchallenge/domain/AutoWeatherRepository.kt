package com.harneeblayze.weatherapppchallenge.domain

import com.harneeblayze.weatherapppchallenge.domain.util.Resource
import com.harneeblayze.weatherapppchallenge.domain.weather.Weather
import kotlinx.coroutines.flow.Flow

interface AutoWeatherRepository {
    fun getWeatherData(): Flow<Resource<Weather>>
}