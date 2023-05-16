package com.harneeblayze.weatherapppchallenge.data.repository

import com.harneeblayze.weatherapppchallenge.R
import com.harneeblayze.weatherapppchallenge.data.Coordinate
import com.harneeblayze.weatherapppchallenge.data.PollingService
import com.harneeblayze.weatherapppchallenge.data.TEN_SECONDS
import com.harneeblayze.weatherapppchallenge.data.coordinates
import com.harneeblayze.weatherapppchallenge.data.mappers.toWeather
import com.harneeblayze.weatherapppchallenge.data.remote.AutoWeatherService
import com.harneeblayze.weatherapppchallenge.domain.AutoWeatherRepository
import com.harneeblayze.weatherapppchallenge.domain.util.Resource
import com.harneeblayze.weatherapppchallenge.domain.weather.Weather
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class AutoWeatherRepositoryImpl @Inject constructor(
    private val autoWeatherService: AutoWeatherService,
    private val pollingService: PollingService
): AutoWeatherRepository {
    override fun getWeatherData(): Flow<Resource<Weather>> = flow{

            pollingService.startPolling()

            var currentWeatherCoordinatesIndex = 0
            while (pollingService.isPolling()) {
                val coordinate = coordinates[currentWeatherCoordinatesIndex]
                fetchFromApi(coordinate = coordinate)
                if (currentWeatherCoordinatesIndex == coordinates.lastIndex) {
                    currentWeatherCoordinatesIndex = 0
                } else {
                    currentWeatherCoordinatesIndex += 1
                }

                delay(TEN_SECONDS)
            }
        }.catch { throwable ->
            pollingService.cancelPolling()
            val errorMessage = when (throwable) {
                is IOException -> "IOException occurred"
                else ->  "An unknown error occurred."
            }
            emit(Resource.Error(errorMessage))
        }

        private suspend fun FlowCollector<Resource<Weather>>.fetchFromApi(coordinate: Coordinate) {
            try {
                emit(
                    Resource.Success(
                        data =
                        autoWeatherService.getAutoWeatherData(
                            lat = coordinate.latitude,
                            long = coordinate.longitude
                        ).toWeather()
                    )
                )

            } catch(e: Exception) {
                pollingService.cancelPolling()
                e.printStackTrace()
                emit(Resource.Error((e.message ?: R.string.exception_error_msg) as String))
            }


        }


}