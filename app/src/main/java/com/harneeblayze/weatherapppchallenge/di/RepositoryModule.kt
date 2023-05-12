package com.harneeblayze.weatherapppchallenge.di


import com.harneeblayze.weatherapppchallenge.data.repository.AutoWeatherRepositoryImpl
import com.harneeblayze.weatherapppchallenge.domain.AutoWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/*@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindWeatherRepository(autoWeatherRepository: AutoWeatherRepositoryImpl): AutoWeatherRepository

}*/


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        autoWeatherRepositoryImpl: AutoWeatherRepositoryImpl
    ): AutoWeatherRepository


}