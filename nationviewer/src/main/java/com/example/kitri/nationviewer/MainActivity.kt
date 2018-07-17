package com.example.kitri.nationviewer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_nation_row.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = getDataFromSource()

        recyclerView_nation.layoutManager = LinearLayoutManager(this)
        recyclerView_nation.adapter = NationAdapter(items)
    }

    private fun getDataFromSource(): List<Nation> {
        return listOf<Nation>(
                Nation(R.drawable.l_flag_argentina, "아르헨티나", "부에노스아이레스"),
                Nation(R.drawable.l_flag_belgium, "벨기에", "브뤼셀"),
                Nation(R.drawable.l_flag_brazil, "브라질", "브라질리아")
        )
    }
}
