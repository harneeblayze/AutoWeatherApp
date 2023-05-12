package com.harneeblayze.weatherapppchallenge.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harneeblayze.weatherapppchallenge.data.PollingService
import com.harneeblayze.weatherapppchallenge.domain.AutoWeatherRepository
import com.harneeblayze.weatherapppchallenge.domain.util.Resource
import com.harneeblayze.weatherapppchallenge.domain.weather.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AutoWeatherViewModel @Inject constructor(
    private val autoWeatherRepository: AutoWeatherRepository,
    private val pollingService: PollingService
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState(isLoading = true))
    val state: StateFlow<WeatherState> = _state.asStateFlow()

    fun computeIntent(weatherIntent: WeatherIntent) {
        when (weatherIntent) {
            is WeatherIntent.LoadWeatherData -> {
                viewModelScope.launch {
                    autoWeatherRepository.getWeatherData().collect { result ->
                        compileResult(result)
                    }
                }
            }

            is WeatherIntent.CancelWeatherDataPolling -> {
                pollingService.cancelPolling()
            }

            is WeatherIntent.DisplayCityName -> {
                setState { copy(cityName = weatherIntent.cityName) }
            }
        }
    }

    private fun compileResult(result: Resource<Weather>) {
        when (result) {
            is Resource.Success -> {
                val weatherData = result.data
                setState {
                    copy(
                        weather = weatherData,
                        isLoading = false,
                        error = null
                    )
                }
            }

            is Resource.Error -> {
                setState {
                    copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    private fun setState(stateReducer: WeatherState.() -> WeatherState) {
        viewModelScope.launch {
            _state.emit(stateReducer(state.value))
        }
    }
}

