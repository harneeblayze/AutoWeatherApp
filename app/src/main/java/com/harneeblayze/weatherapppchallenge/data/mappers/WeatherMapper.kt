package com.harneeblayze.weatherapppchallenge.data.mappers

import com.harneeblayze.weatherapppchallenge.data.remote.AutoWeatherDto
import com.harneeblayze.weatherapppchallenge.data.remote.CurrentWeatherDto
import com.harneeblayze.weatherapppchallenge.data.remote.HourlyDto
import com.harneeblayze.weatherapppchallenge.domain.weather.CurrentWeather
import com.harneeblayze.weatherapppchallenge.domain.weather.HourlyWeather
import com.harneeblayze.weatherapppchallenge.domain.weather.Weather
import com.harneeblayze.weatherapppchallenge.domain.weather.Unit
import com.harneeblayze.weatherapppchallenge.domain.weather.WeatherInfo
import com.harneeblayze.weatherapppchallenge.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt


fun AutoWeatherDto.toWeather(): Weather = Weather(
    latitude = latitude,
    longitude = longitude,
    hourlyWeather = mapDatesToHourly(hourly),
    currentWeather = mapToCurrentWeather(currentWeather)
)


private fun mapToCurrentWeather(currentWeather: CurrentWeatherDto): CurrentWeather {
    val formattedTime = OffsetDateTime.parse(currentWeather.time + 'Z')
    val time = formattedTime.formattedDateToHourlyTime("HH:mm")
    return CurrentWeather(
        temperature = formatTemperatureValue(
            temperature = currentWeather.temperature,
            unit = Unit.METRIC.value
        ),
        time = time
    )
}


private fun mapDatesToHourly(hourly: HourlyDto): HourlyWeather {
    val weatherInfoList = mutableListOf<WeatherInfo>()
    for ((index, time) in hourly.time.withIndex()) {
        val temperature = hourly.temperatures[index]
        val formattedTemperature =
            formatTemperatureValue(temperature = temperature, unit = Unit.METRIC.value)
        val offsetDateTime = OffsetDateTime.parse(time + 'Z')
        val dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
        val formattedTime = dateTimeFormatter.format(offsetDateTime)
        val weatherInfo = WeatherInfo(time = formattedTime, temperature = formattedTemperature,
        weatherType = WeatherType.humidityConverter(temperature.roundToInt()))
        weatherInfoList.add(weatherInfo)
    }
    return HourlyWeather(data = weatherInfoList)
}


fun OffsetDateTime.formattedDateToHourlyTime(time: String, locale: Locale = Locale.getDefault()):
        String =
    this.formattedDateToHourlyTime(DateTimeFormatter.ofPattern(time, locale))

fun OffsetDateTime.formattedDateToHourlyTime(formatter: DateTimeFormatter):
        String = formatter.format(LocalDateTime.ofInstant(this.toInstant(), ZoneId.systemDefault()))


private fun formatTemperatureValue(temperature: Float, unit: String): String =
    "${temperature.roundToInt()}${getUnitSymbols(unit = unit)}"

private fun getUnitSymbols(unit: String) = when (unit) {
    Unit.METRIC.value -> Unit.METRIC.tempLabel
    Unit.IMPERIAL.value -> Unit.IMPERIAL.tempLabel
    else -> "undefined"
}