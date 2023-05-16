package com.harneeblayze.weatherapppchallenge

import app.cash.turbine.test
import com.harneeblayze.weatherapppchallenge.data.PollingService
import com.harneeblayze.weatherapppchallenge.data.remote.AutoWeatherService
import com.harneeblayze.weatherapppchallenge.data.repository.AutoWeatherRepositoryImpl
import com.harneeblayze.weatherapppchallenge.domain.AutoWeatherRepository
import com.harneeblayze.weatherapppchallenge.domain.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AutoWeatherRepositoryTest {
    private val autoWeatherService: AutoWeatherService = mockk(relaxed = true)
    private lateinit var pollingService: PollingService
    private lateinit var fakeWeatherRepository: AutoWeatherRepository

    @Before
    fun setUp() {
        pollingService = PollingService
        fakeWeatherRepository = AutoWeatherRepositoryImpl(autoWeatherService, pollingService)

    }

    @Test
    fun `get weather data from endpoint and assert that for a success result, a mapped data model is emitted`() =
        runTest {
            coEvery { autoWeatherService.getAutoWeatherData(any(), any()) } returns
                    fakeSuccessResp

                 fakeWeatherRepository.getWeatherData().test {
                     awaitItem().also { data ->

                         assertThat(data, instanceOf(Resource.Success::class.java))
                         assert((data as Resource.Success).data?.equals(fakeMapperWeatherResp) == true)
                     }
                 }
        }
    @Test
    fun `when weather data throws an exception, an error state is emitted`() = runTest {
        coEvery { autoWeatherService.getAutoWeatherData(any(), any()) }throws Exception()

        val error = "An unknown error occurred."
        fakeWeatherRepository.getWeatherData().test {
            awaitItem().also { data ->

                assertThat(data, instanceOf(Resource.Error::class.java))
                assert((data as Resource.Error).message.equals(error))
            }
            awaitComplete()
        }
    }

    @Test
    fun `get weather data and start polling every 10secs`() = runTest {

        coEvery { autoWeatherService.getAutoWeatherData(any(), any()) } returns
                    fakeSuccessResp

        fakeWeatherRepository.getWeatherData().test {
            awaitItem()
        }

        assert(PollingService.isPolling())
    }

    @Test
    fun `when weather data has error, polling stops`() = runTest {
        coEvery { autoWeatherService.getAutoWeatherData(any(), any()) }throws Exception()
        fakeWeatherRepository.getWeatherData().test {
            awaitItem()
            awaitComplete()
        }
        assert(!PollingService.isPolling())
    }


}