package com.binaryleaf.openweathercrudassignment.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.binaryleaf.openweathercrudassignment.model.WeatherData
import com.binaryleaf.openweathercrudassignment.model.WeatherDataProcessor
import com.binaryleaf.openweathercrudassignment.utils.DBHelper

class WeatherDataViewModel @JvmOverloads constructor(app: Application, val weatherDataProcessor: WeatherDataProcessor = WeatherDataProcessor()) : ObservableViewModel(app), WeatherDataProcessor.OnWeatherDataListener {
    var lastWeatherData = WeatherData()
    var weatherDataListener: WeatherDataProcessor.OnWeatherDataListener? = null
    val db by lazy { DBHelper(app) }

    init {
        weatherDataListener = this
        weatherDataProcessor.getWeatherData(this)
    }

    override fun onSuccess(weatherData: WeatherData) {
        weatherDataProcessor.saveWeatherData(weatherData)
        updateOutputs(weatherData)
        db.insertData(weatherData)
    }

    fun updateOutputs(wd: WeatherData) {
        lastWeatherData = wd
        notifyChange()
    }

    fun loadSavedWeatherDataSummaries(): LiveData<List<WeatherDataSummaryItem>> {
        weatherDataProcessor.mergeLocalDataList(db.readData())
        return Transformations.map(weatherDataProcessor.loadWeatherData()) { weatherDataObjects ->
            weatherDataObjects.map {
                WeatherDataSummaryItem(it.temp,
                        it.dateCreated)
            }
        }
    }
}