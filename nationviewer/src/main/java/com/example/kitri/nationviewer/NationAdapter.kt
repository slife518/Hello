package com.example.kitri.nationviewer

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class NationAdapter(val items: List<Nation>) : RecyclerView.Adapter<NationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.layout_nation_row, parent, false)
        return NationViewHolder(row)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NationViewHolder, position: Int) {
        holder.imgFlag.setImageResource(items[position].imgFlag)
        holder.textNation.text = items[position].nationName
        holder.textCaptital.text = items[position].capital
    }

}

class NationViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener {
//            Toast.makeText(view.context, "event..", Toast.LENGTH_LONG).show()
            val intent = Intent(view.context, NationDetailActivity::class.java)
            intent.putExtra(
                    "nation_name",
                    view.findViewById<TextView>(R.id.textView_nation_name).text)
            view.context.startActivity(intent)
        }
    }

    val imgFlag = view.findViewById<ImageView>(R.id.imageView_flag)
    val textNation = view.findViewById<TextView>(R.id.textView_nation_name)
    val textCaptital = view.findViewById<TextView>(R.id.textView_capital)
}

data class Nation(val imgFlag: Int,
                  val nationName: String,
                  val capital: String)
