package com.platonefimov.weatherapp.data.server

import android.util.Log
import com.google.gson.Gson
import java.io.IOException
import java.net.URL

class ForecastByZipCodeRequest(private val zipCode: Long, private val gson: Gson = Gson()) {

    companion object {
        private val APP_ID = "a163eb06ba400dc97554789d6d39fc18"
        private val URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json" +
                "&units=metric&cnt=7"
        private val COMPLETE_URL = "$URL&APPID=$APP_ID&zip="
    }

    fun execute(): ForecastResult? {
        return try {
            val forecastJsonStr = URL(COMPLETE_URL + zipCode + ",ru").readText()
            gson.fromJson(forecastJsonStr, ForecastResult::class.java)
        } catch (e: IOException) {
            Log.e(javaClass.name, "IOException")
            null
        }
    }
}
