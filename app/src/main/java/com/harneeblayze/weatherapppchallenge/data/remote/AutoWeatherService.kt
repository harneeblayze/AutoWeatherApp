package com.harneeblayze.weatherapppchallenge.data.remote

import com.harneeblayze.weatherapppchallenge.domain.weather.Unit
import retrofit2.http.GET
import retrofit2.http.Query

interface AutoWeatherService {

    @GET("v1/forecast")
    suspend fun getAutoWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double,
        @Query("hourly") hourly:String = "temperature_2m",
        @Query("temperature_unit") temperatureUnit: String = Unit.METRIC.value,
        @Query("forecast_days") forecastDays: Int = 1,
        @Query("current_weather") includeCurrentWeather: Boolean = true
    ): AutoWeatherDto
}