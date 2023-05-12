package com.harneeblayze.weatherapppchallenge

import com.harneeblayze.weatherapppchallenge.data.remote.AutoWeatherDto
import com.harneeblayze.weatherapppchallenge.data.remote.CurrentWeatherDto
import com.harneeblayze.weatherapppchallenge.data.remote.HourlyDto
import com.harneeblayze.weatherapppchallenge.data.remote.HourlyUnitsDto
import com.harneeblayze.weatherapppchallenge.domain.weather.CurrentWeather
import com.harneeblayze.weatherapppchallenge.domain.weather.HourlyWeather
import com.harneeblayze.weatherapppchallenge.domain.weather.Weather
import com.harneeblayze.weatherapppchallenge.domain.weather.WeatherInfo
import com.harneeblayze.weatherapppchallenge.domain.weather.WeatherType

val fakeHourlyDto = HourlyDto(
    time = listOf("2023-05-11T18:00"),
    temperatures = listOf(17.0f),
)

val fakeHourlyUnitsDto = HourlyUnitsDto(
    time = "",
    temperature = "°C",
)

val currentWeatherDto = CurrentWeatherDto(
    time = "2023-05-11T18:00",
    temperature = 12.17f,
)

val fakeSuccessResp = AutoWeatherDto(
    longitude = 13.6,
    latitude = 12.12,
    timezone = "",
    hourly = fakeHourlyDto,
    hourlyUnits = fakeHourlyUnitsDto,
    currentWeather = currentWeatherDto
)

val fakeMapperWeatherResp = Weather(
    longitude = 13.6,
    latitude = 12.12,
    hourlyWeather = HourlyWeather(data = listOf(WeatherInfo(temperature = "17°C", time = "18:00",
    weatherType = WeatherType.humidityConverter(17)))),
    currentWeather = CurrentWeather(
        temperature = "14°C",
        time = "18:00"
    )
)