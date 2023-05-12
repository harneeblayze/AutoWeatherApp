package com.harneeblayze.weatherapppchallenge.di


import com.harneeblayze.weatherapppchallenge.data.BASE_URL
import com.harneeblayze.weatherapppchallenge.data.PollingService
import com.harneeblayze.weatherapppchallenge.data.remote.AutoWeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAutoWeatherService(): AutoWeatherService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    internal fun providesPollingService() = PollingService
}