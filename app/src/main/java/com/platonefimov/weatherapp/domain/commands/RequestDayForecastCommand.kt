package com.platonefimov.weatherapp.domain.commands

import com.platonefimov.weatherapp.domain.datasource.ForecastProvider
import com.platonefimov.weatherapp.domain.model.Forecast

class RequestDayForecastCommand(val id: Long,
                                private val forecastProvider: ForecastProvider =
                                ForecastProvider()) :
        Command<Forecast> {

    override fun execute(): Forecast = forecastProvider.requestForecast(id)
}
