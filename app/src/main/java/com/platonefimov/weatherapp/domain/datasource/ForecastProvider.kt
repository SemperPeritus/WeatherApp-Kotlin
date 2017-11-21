package com.platonefimov.weatherapp.domain.datasource

import com.platonefimov.weatherapp.data.db.ForecastDb
import com.platonefimov.weatherapp.data.server.ForecastServer
import com.platonefimov.weatherapp.domain.model.Forecast
import com.platonefimov.weatherapp.domain.model.ForecastList
import com.platonefimov.weatherapp.extensions.firstResult

class ForecastProvider(private val sources: List<ForecastDataSource> = ForecastProvider.SOURCES) {

    companion object {
        val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES by lazy { listOf(ForecastDb(), ForecastServer()) }
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList = requestToSources {
        val result = it.requestForecastByZipCode(zipCode, todayTimeSpan())
        if (result != null && result.size >= days) result else null
    }

    fun requestForecast(id: Long): Forecast = requestToSources {
        it.requestDayForecast(id)
    }

    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS

    private fun <T : Any> requestToSources(f: (ForecastDataSource) -> T?): T =
            sources.firstResult { f(it) }
}
