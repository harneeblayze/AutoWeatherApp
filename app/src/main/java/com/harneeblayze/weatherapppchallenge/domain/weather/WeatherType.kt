package com.harneeblayze.weatherapppchallenge.domain.weather

import androidx.annotation.DrawableRes
import com.harneeblayze.weatherapppchallenge.R

sealed class WeatherType(
    val weatherDesc: String,
    @DrawableRes val iconRes: Int
) {
    object ClearSky : WeatherType(
        weatherDesc = "Clear sky",
        iconRes = R.drawable.ic_sunny
    )
    object MainlyClear : WeatherType(
        weatherDesc = "Mainly clear",
        iconRes = R.drawable.ic_cloudy
    )
    object PartlyCloudy : WeatherType(
        weatherDesc = "Partly cloudy",
        iconRes = R.drawable.ic_cloudy
    )

    object HeavyRain : WeatherType(
        weatherDesc = "Heavy rain",
        iconRes = R.drawable.ic_rainy
    )

    object SlightSnowFall: WeatherType(
        weatherDesc = "Slight snow fall",
        iconRes = R.drawable.ic_snowy
    )

    object SlightRainShowers: WeatherType(
        weatherDesc = "Slight rain showers",
        iconRes = R.drawable.ic_rainshower
    )
    object HeavySnowShowers: WeatherType(
        weatherDesc = "Heavy snow showers",
        iconRes = R.drawable.ic_snowy
    )


    companion object {
        fun humidityConverter(code: Int): WeatherType {
            return when(code) {
               in(15..20) -> ClearSky
                in(13..15) -> MainlyClear
                in(9..12) -> PartlyCloudy
                in(2..6) -> HeavyRain
                in(0..3) -> SlightSnowFall
                in(7..9) -> SlightRainShowers
                in(-1 downTo -8) -> HeavySnowShowers
                else -> ClearSky
            }
        }
    }
}