package com.harneeblayze.weatherapppchallenge.data



import java.util.concurrent.atomic.AtomicBoolean



object PollingService {

    private var polling: AtomicBoolean = AtomicBoolean(true)

    fun isPolling() = polling.get()

    fun startPolling() {
        polling.compareAndSet(false, true)
    }

    fun cancelPolling() {
        polling.compareAndSet(true, false)
    }


}