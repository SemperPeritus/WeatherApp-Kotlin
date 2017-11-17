package com.platonefimov.weatherapp.domain.datasource

import com.platonefimov.weatherapp.domain.model.Forecast
import com.platonefimov.weatherapp.domain.model.ForecastList

interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?
    fun requestDayForecast(id: Long): Forecast?
}
