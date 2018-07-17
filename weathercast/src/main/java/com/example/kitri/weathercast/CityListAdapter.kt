package com.example.kitri.weathercast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.kitri.weathercast.data.City

class CityListAdapter(context: Context, cities: List<City>)
        : ArrayAdapter<City>(context, R.layout.layout_city_row, cities) {

    // data binding
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val row = convertView ?: inflater.inflate(R.layout.layout_city_row, parent, false)
        val cityName = row!!.findViewById<TextView>(R.id.textView_cityName)
        cityName.text = getItem(position).name

        row.tag = getItem(position).id

        return row
    }
}