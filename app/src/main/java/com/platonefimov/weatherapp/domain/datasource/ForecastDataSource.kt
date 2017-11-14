package com.platonefimov.weatherapp.domain.datasource

import com.platonefimov.weatherapp.domain.model.ForecastList

interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?
}
