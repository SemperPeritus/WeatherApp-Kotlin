package com.platonefimov.weatherapp.domain.commands

import com.platonefimov.weatherapp.data.ForecastRequest
import com.platonefimov.weatherapp.domain.mappers.ForecastDataMapper
import com.platonefimov.weatherapp.domain.model.ForecastList

class RequestForecastCommand(private val city: String) : Command<ForecastList> {

    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(city)
        return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
    }
}
