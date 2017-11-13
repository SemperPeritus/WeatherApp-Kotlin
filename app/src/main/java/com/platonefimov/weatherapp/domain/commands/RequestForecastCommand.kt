package com.platonefimov.weatherapp.domain.commands

import com.platonefimov.weatherapp.data.server.ForecastRequest
import com.platonefimov.weatherapp.domain.mappers.ForecastDataMapper
import com.platonefimov.weatherapp.domain.model.ForecastList

class RequestForecastCommand(private val zipCode: Long) : Command<ForecastList> {

    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(zipCode, forecastRequest.execute())
    }
}
