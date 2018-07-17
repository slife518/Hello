package com.example.kitri.weathercast

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.kitri.weathercast.data.WeatherForecast
import com.example.kitri.weathercast.data.WeekList
import com.squareup.picasso.Picasso


class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val descript: TextView = itemView.findViewById(R.id.descript) as TextView
    val weatherIcon: ImageView = itemView.findViewById(R.id.weather_icon) as ImageView
    val currentTemp: TextView = itemView.findViewById(R.id.current_temp) as TextView
    val highRowTemp: TextView = itemView.findViewById(R.id.high_row_temp) as TextView
    val cityName: TextView = itemView.findViewById(R.id.city_name) as TextView
    val humidity: TextView = itemView.findViewById(R.id.humidity) as TextView
    val cloudy: TextView = itemView.findViewById(R.id.cloudy) as TextView
    val wind: TextView = itemView.findViewById(R.id.wind) as TextView
    val forecast: ForecastView = itemView.findViewById(R.id.forecast) as ForecastView
    val delBtn: ImageButton = itemView.findViewById(R.id.del_btn) as ImageButton

    fun bindHolder(data: WeatherForecast, delClick: View.OnClickListener) {
        descript.text = data.current[0].description
        weatherIcon.loadUrl(data.iconUrl + "${data.current[0].icon}.png")
        currentTemp.text = String.format("%s \u2103", data.current.main.temp)

        val format: String = "%s \u2103 / %s \u2103"
        highRowTemp.text = String.format(format, data.current.main.temp_min, data.current.main.temp_max)
        cityName.text = data.current.name
        cloudy.text = String.format("%s %%", data.current.clouds.all)
        humidity.text = String.format("%s %%", data.current.main.humidity)
        wind.text = data.current.wind.speed
        forecast.setView(data.week.list as ArrayList<WeekList>, data.iconUrl)
        delBtn.setOnClickListener(delClick)
        delBtn.tag = data.current.id
    }

}

fun ImageView.loadUrl(url: String) {
    Picasso.get().load(url).into(this)
}

class WeatherListViewAdapter(val data: ArrayList<WeatherForecast>)
    : RecyclerView.Adapter<ViewHolder>() {

    var mWeatherData = ArrayList<WeatherForecast>(data)
    var delBtnClickListener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val mainView = inflater.inflate(R.layout.layout_card, parent, false)
        return ViewHolder(mainView)
    }

    override fun getItemCount(): Int = mWeatherData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mWeatherData[position]
        holder.bindHolder(data, delBtnClickListener!!)
    }

    fun updateData(newData: ArrayList<WeatherForecast>): Unit {
        mWeatherData.clear()
        mWeatherData.addAll(newData)
        notifyDataSetChanged()
    }

    fun removeData(apiId: String): Unit {

        for ( weatherData in mWeatherData ) {
            if ( weatherData.current.id == apiId ) {
                mWeatherData.remove(weatherData)
                break
            }
        }

        notifyDataSetChanged()
    }
}