package com.example.kitri.weathercast

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.kitri.weathercast.data.Cities
import com.example.kitri.weathercast.data.City
import com.example.kitri.weathercast.db.DBHandlerAnko
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_select_city.*
import java.io.InputStreamReader

class SelectCityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_city)

        val koreanCities = getKoreanCityFromJson()

        listView_cityList.adapter =
                CityListAdapter(this, koreanCities.cities)

        listView_cityList.setOnItemClickListener { adapterView, view, i, l ->
            val cityName =
                    view.findViewById<TextView>(R.id.textView_cityName).text
            val cityId = view.tag as String

//            Toast.makeText(this, "$cityId : $cityName", Toast.LENGTH_LONG).show()

            val dbHandler = DBHandlerAnko(this)
            dbHandler.saveCity(City(cityId, cityName as String))

            setResult(RESULT_OK)
            finish()
        }
    }

    private fun getKoreanCityFromJson(): Cities {
        val gson = GsonBuilder().create()
        val reader = InputStreamReader(assets.open("city.list.json"))

        var cityArray = gson.fromJson(reader, Array<City>::class.java)
        var cities = Cities(cityArray.toList())

        cities.cities = cities.cities.filter { city -> city.country.equals("KR") }

        return cities
    }
}
