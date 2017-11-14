package com.platonefimov.weatherapp.data.db

import com.platonefimov.weatherapp.domain.datasource.ForecastDataSource
import com.platonefimov.weatherapp.domain.model.ForecastList
import com.platonefimov.weatherapp.extensions.clear
import com.platonefimov.weatherapp.extensions.parseList
import com.platonefimov.weatherapp.extensions.parseOpt
import com.platonefimov.weatherapp.extensions.toVarargArray
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class ForecastDb(private val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
                 private val dataMapper: DbDataMapper = DbDataMapper()) : ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {
        val dailyRequest = "${DayForecastTable.CITY_ID} = {id}" +
                " AND ${DayForecastTable.DATE} >= {date}"
        val dailyForecast = select(DayForecastTable.NAME)
                .whereArgs(dailyRequest, "id" to zipCode, "date" to date)
                .parseList { DayForecast(HashMap(it)) }

        val city = select(CityForecastTable.NAME)
                .whereArgs("${CityForecastTable.ID} = {id}", "id" to zipCode)
                .parseOpt { CityForecast(HashMap(it), dailyForecast) }

        if (city != null)
            dataMapper.convertToDomain(city)
        else
            null
    }

    fun saveForecast(forecast: ForecastList) = forecastDbHelper.use {
        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)

        with(dataMapper.convertFromDomain(forecast)) {
            insert(CityForecastTable.NAME, *map.toVarargArray())
            dailyForecast.forEach { insert(DayForecastTable.NAME, *it.map.toVarargArray()) }
        }
    }
}
