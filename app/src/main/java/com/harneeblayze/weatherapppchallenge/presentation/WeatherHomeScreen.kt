package com.harneeblayze.weatherapppchallenge.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.harneeblayze.weatherapppchallenge.domain.weather.HourlyWeather
import com.harneeblayze.weatherapppchallenge.domain.weather.WeatherInfo
import com.harneeblayze.weatherapppchallenge.ui.theme.DarkBlue
import com.harneeblayze.weatherapppchallenge.ui.theme.DeepBlue
import com.harneeblayze.weatherapppchallenge.ui.theme.Dimens

@Composable
fun WeatherHomeScreen(
    state: WeatherState,
    onTryAgainClicked: () -> Unit,
    cityLocale: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        state.weather?.let { weather ->
            LocalContext.current.getCityName(
                latitude = weather.latitude,
                longitude = weather.longitude
            ) { address ->
                cityLocale(address.locality)
            }
        }

        if (state.isLoading) {
            Spacer(modifier = Modifier.weight(0.5f))
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(Dimens.dp_medium)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.weight(0.5f))
        }

        if (state.error != null) {
            Spacer(modifier = Modifier.weight(0.5f))
            ErrorItem(
                errorId = state.error,
                modifier = Modifier.padding(Dimens.dp_medium)
            ) {
                onTryAgainClicked()
            }
            Spacer(modifier = Modifier.weight(0.5f))
        }
        WeatherDisplayCard(state = state , backgroundColor =
        DeepBlue)

        state.weather?.hourlyWeather?.let { hourlyWeather ->
            WeatherForecastList(hourlyWeather)
        }
    }
}



@Composable
private fun WeatherForecastList(hourlyWeather: HourlyWeather) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.dp_medium)
    ) {
            Text(
                text = "Today",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )

        Spacer(modifier = Modifier.height(Dimens.dp_medium))
        LazyRow(content = {
            items(hourlyWeather.data) { weatherData ->
                HourlyWeatherDisplay(
                    hourlyWeather = weatherData,
                    modifier = Modifier
                        .padding(horizontal = Dimens.dp_medium)
                )
            }
        })
    }
}


@Composable
fun HourlyWeatherDisplay(
    hourlyWeather: WeatherInfo,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = hourlyWeather.time,
            color = Color.LightGray
        )
        Image(
            painter = painterResource(id = hourlyWeather.weatherType.iconRes),
            contentDescription = null,
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = hourlyWeather.temperature,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
private fun ErrorItem(errorId: String, modifier: Modifier, onTryAgainClicked: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text =  errorId,
            textAlign = TextAlign.Center,
            modifier = modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            onClick = { onTryAgainClicked() },
            modifier = Modifier
                .padding(Dimens.dp_medium)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Error Occurred Please try again",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}