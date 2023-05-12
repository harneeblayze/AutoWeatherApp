package com.harneeblayze.weatherapppchallenge.domain.weather

enum class Unit(val value: String, val tempLabel: String) {
    METRIC("celsius","°C"),
    IMPERIAL("fahrenheit","°F"),
}