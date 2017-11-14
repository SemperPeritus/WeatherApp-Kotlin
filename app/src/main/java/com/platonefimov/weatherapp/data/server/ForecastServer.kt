package com.platonefimov.weatherapp.data.server

import com.platonefimov.weatherapp.data.db.ForecastDb
import com.platonefimov.weatherapp.domain.datasource.ForecastDataSource
import com.platonefimov.weatherapp.domain.model.ForecastList

class ForecastServer(private val dataMapper: ServerDataMapper = ServerDataMapper(),
                     private val forecastDb: ForecastDb = ForecastDb()) : ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastByZipCodeRequest(zipCode).execute()
        return if (result != null) {
            val converted = dataMapper.convertToDomain(zipCode, result)
            forecastDb.saveForecast(converted)
            forecastDb.requestForecastByZipCode(zipCode, date)
        } else
            null
    }
}
