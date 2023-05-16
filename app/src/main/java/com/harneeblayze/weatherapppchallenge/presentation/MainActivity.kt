package com.harneeblayze.weatherapppchallenge.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.harneeblayze.weatherapppchallenge.presentation.ui.theme.WeatherApppChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val autoWeatherViewModel: AutoWeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherApppChallengeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state = autoWeatherViewModel
                        .state
                        .collectAsState()
                        .value

                    WeatherHomeScreen(
                        state = state,
                        onTryAgainClicked = { autoWeatherViewModel.computeIntent(WeatherIntent.LoadWeatherData) },
                        cityLocale = {autoWeatherViewModel.computeIntent(WeatherIntent.DisplayCityName(cityName = it))},

                    )

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        autoWeatherViewModel.computeIntent(WeatherIntent.LoadWeatherData)
    }

    override fun onStop() {
        super.onStop()
        autoWeatherViewModel.computeIntent(WeatherIntent.CancelWeatherDataPolling)
    }
}


