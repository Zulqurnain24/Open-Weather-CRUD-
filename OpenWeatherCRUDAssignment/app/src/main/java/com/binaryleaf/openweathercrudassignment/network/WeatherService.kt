package com.binaryleaf.openweathercrudassignment.network

import com.binaryleaf.openweathercrudassignment.network.Response.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Mohammad Zulqurnain on 11/18/2019.
 */
interface WeatherService {

    @GET("/data/2.5/weather")
    fun getWeather(@Query("id") id: String, @Query("appid") appId: String, @Query("units") unit: String, @Query("lat") lat: Double, @Query("lon") lon: Double): Call<WeatherResponse>
}