package com.example.kitri.hello2

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val rvMain = findViewById(R.id.recyclerView_main) as RecyclerView
//        rvMain.setBackgroundColor(Color.RED)

//        recyclerView_main.setBackgroundColor(Color.CYAN)

        button_main.setOnClickListener {
            println("button clicked..")
        }
    }
}
