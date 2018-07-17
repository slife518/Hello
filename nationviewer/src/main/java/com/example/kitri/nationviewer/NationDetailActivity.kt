package com.example.kitri.nationviewer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_nation_detail.*
import kotlinx.android.synthetic.main.layout_nation_row.*
import java.io.InputStreamReader

class NationDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nation_detail)

        val nationName = intent.getStringExtra("nation_name")

        val nationDetail = getNationDetailFromName(nationName)

        text_detail_name.text = nationDetail?.name
        text_detail_capital.text = nationDetail?.capital
        text_detail_language.text = nationDetail?.language
        text_detail_location.text = nationDetail?.location
        text_detail_weather.text = nationDetail?.weather
        text_detail_volume.text = nationDetail?.volume

        img_flag.setImageResource(getImageIdFromName(nationDetail?.name))
    }

    private fun getImageIdFromName(nationName: String?): Int {
        return when(nationName) {
            "브라질" -> R.drawable.l_flag_brazil
            "아르헨티나" -> R.drawable.l_flag_argentina
            "벨기에" -> R.drawable.l_flag_belgium
            else -> R.drawable.l_flag_china
        }
    }

    private fun getNationDetailFromName(selected: String): NationDetail? {

        val gson = GsonBuilder().create()
        val inputStream = assets.open("nation_data.json")
        val items = gson.fromJson(InputStreamReader(inputStream), NationDetails::class.java)

        for (item in items.data) {
            if (selected.equals(item.name))
                return item
        }
        return null
    }
}
