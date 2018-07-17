package com.example.kitri.weathercast

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import com.example.kitri.weathercast.data.City
import com.example.kitri.weathercast.data.DayData
import com.example.kitri.weathercast.data.WeatherForecast
import com.example.kitri.weathercast.data.WeekData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class ForecastDataLoader(context: Context, val cities: List<City>)
    : AsyncTaskLoader<List<WeatherForecast>>(context) {

    val url = "http://api.openweathermap.org/data/2.5/"

    companion object {
        val API_KEY = "5df3f1b99c9227bb6bb15d61c2b5bf9b"
        val ICON_URL = "http://openweathermap.org/img/w/"
    }

    override fun loadInBackground(): List<WeatherForecast> {

        val cityWeather = ArrayList<WeatherForecast>()

//        for (city in cities) {
//            val forecast = getWeatherData(url, city.id)
//            cityWeather.add(forecast!!)
//        }

        cities.forEach {
            val forecast = getWeatherData(url, it.id)
            cityWeather.add(forecast!!)
        }

        return cityWeather
    }

    private fun getWeatherData(url: String, cityId: String): WeatherForecast? {
        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(UrlInterface::class.java)
        val dayDataCall = service.getWeather(cityId)
        val dayDataCallResponse = dayDataCall.execute()

        if (dayDataCallResponse.isSuccessful) {
            val weekDataCall = service.getForecast(cityId)
            val weekDataResponse = weekDataCall.execute()

            if (weekDataResponse.isSuccessful) {
                return WeatherForecast(
                        dayDataCallResponse.body()!!,
                        weekDataResponse.body()!!,
                        ICON_URL)
            }
        }
        return null
    }

    private interface UrlInterface {
        @GET("weather")
        fun getWeather(@Query("id") id: String,
                       @Query("units") units: String = "metrics",
                       @Query("APPID") appid: String = API_KEY): Call<DayData>

        @GET("forecast")
        fun getForecast(@Query("id") id: String,
                        @Query("units") units: String = "metrics",
                        @Query("APPID") appid: String = API_KEY): Call<WeekData>
    }
}