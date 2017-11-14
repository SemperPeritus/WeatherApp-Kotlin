package com.platonefimov.weatherapp.domain.datasource

import android.util.Log
import com.platonefimov.weatherapp.data.db.ForecastDb
import com.platonefimov.weatherapp.data.server.ForecastServer
import com.platonefimov.weatherapp.domain.model.ForecastList
import com.platonefimov.weatherapp.extensions.firstResult

class ForecastProvider(private val sources: List<ForecastDataSource> = ForecastProvider.SOURCES) {

    companion object {
        val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES = listOf(ForecastDb(), ForecastServer())
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList =
            sources.firstResult { requestSource(it, days, zipCode) }

    private fun requestSource(source: ForecastDataSource, days: Int, zipCode: Long): ForecastList? {
        val result = source.requestForecastByZipCode(zipCode, todayTimeSpan())
        Log.v(this.javaClass.name, if (result != null) result::class.simpleName +
                result.size.toString() else "Null")
        return if (result != null && result.size >= days) result else null
    }

    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS
}
