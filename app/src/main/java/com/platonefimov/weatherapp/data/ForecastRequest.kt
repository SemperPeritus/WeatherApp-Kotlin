package com.platonefimov.weatherapp.data

import com.google.gson.Gson
import java.net.URL

class ForecastRequest(val zipCode: String) {

    companion object {
        private val APP_ID = "a163eb06ba400dc97554789d6d39fc18"
        private val URL = "http://api.openweathermap.org/data/2.5/forecast/daily" +
                "?mode=json&units=metric&cnt=7"
        private val COMPLETE_URL = "$URL&APPID=$APP_ID&q="
    }

    fun execute(): ForecastResult {
        val forecastJsonStr = URL(COMPLETE_URL + zipCode).readText()
        return Gson().fromJson(forecastJsonStr, ForecastResult::class.java)
    }
}
