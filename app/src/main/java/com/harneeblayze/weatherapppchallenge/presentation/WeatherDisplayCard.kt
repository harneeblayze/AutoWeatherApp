package com.harneeblayze.weatherapppchallenge.presentation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.harneeblayze.weatherapppchallenge.R
import com.harneeblayze.weatherapppchallenge.ui.theme.Dimens


@Composable
fun WeatherDisplayCard(
    state: WeatherState,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    state.weather?.let { data ->
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .padding(Dimens.dp_medium),
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.dp_medium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Today ${
                        data.currentWeather.time
                    }",
                    modifier = Modifier.align(Alignment.End),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(Dimens.dp_medium))
                CloudAnimation(250.dp, R.raw.switchdaynight)
                Spacer(modifier = Modifier.height(Dimens.dp_medium))
                Text(
                    text = data.currentWeather.temperature,
                    fontSize = Dimens.sp_extraLarge,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(Dimens.dp_medium))

                Text(
                    text = state.cityName,
                    fontSize = Dimens.sp_large,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(Dimens.dp_large))
            }
        }
    }
}

