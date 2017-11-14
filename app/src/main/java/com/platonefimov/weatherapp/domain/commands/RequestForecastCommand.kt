package com.platonefimov.weatherapp.domain.commands

import com.platonefimov.weatherapp.domain.datasource.ForecastProvider
import com.platonefimov.weatherapp.domain.model.ForecastList

class RequestForecastCommand(private val zipCode: Long,
                             private val forecastProvider: ForecastProvider = ForecastProvider()) :
        Command<ForecastList> {

    companion object {
        val DAYS = 7
    }

    override fun execute(): ForecastList = forecastProvider.requestByZipCode(zipCode, DAYS)
}
