package com.platonefimov.weatherapp.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.platonefimov.weatherapp.R
import com.platonefimov.weatherapp.domain.commands.RequestForecastCommand
import com.platonefimov.weatherapp.domain.model.Forecast
import com.platonefimov.weatherapp.ui.adapters.ForecastListAdapter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val forecastList = findViewById<RecyclerView>(R.id.forecast_list)
        forecastList.layoutManager = LinearLayoutManager(this)

        doAsync {
            val result = RequestForecastCommand("634045").execute()
            uiThread {
                forecastList.adapter = ForecastListAdapter(result,
                        object : ForecastListAdapter.OnItemClickListener {
                            override fun invoke(forecast: Forecast) {
                                toast(forecast.date)
                            }
                        })
            }
        }
    }
}
