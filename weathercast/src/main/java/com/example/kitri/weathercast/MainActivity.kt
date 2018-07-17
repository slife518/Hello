package com.example.kitri.weathercast

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.kitri.weathercast.data.City
import com.example.kitri.weathercast.data.WeatherForecast
import com.example.kitri.weathercast.db.DBHandlerAnko
import kotlinx.android.synthetic.main.activity_main.*

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<List<WeatherForecast>> {
    val dbHandler = DBHandlerAnko(this)
    var adapter: WeatherListViewAdapter? = null
    val citiesSelected = ArrayList<City>()
    companion object {
        val LOADER_ID = 101010
        val REQUEST_SELECT_CITY = 100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        citiesSelected.clear()
        citiesSelected.addAll(dbHandler.getCityDataAll())

        supportLoaderManager.initLoader(LOADER_ID, null, this)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuItem_addCity -> {
                val intent = Intent(this, SelectCityActivity::class.java)
                startActivityForResult(intent, REQUEST_SELECT_CITY)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SELECT_CITY && resultCode == RESULT_OK) {
            citiesSelected.clear()
            citiesSelected.addAll(dbHandler.getCityDataAll())

            supportLoaderManager.restartLoader(LOADER_ID, null, this)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<WeatherForecast>> {
        val loader = ForecastDataLoader(this, citiesSelected)
        loader.forceLoad()
        progressBar.visibility = View.VISIBLE
        return loader
    }

    override fun onLoadFinished(loader: Loader<List<WeatherForecast>>,
                                data: List<WeatherForecast>?) {
        adapter ?: let {
            adapter = WeatherListViewAdapter(data as ArrayList<WeatherForecast>)
            adapter?.delBtnClickListener = View.OnClickListener {
                view ->
                val db = DBHandlerAnko(applicationContext)
                db.deleteCity(view.tag as String)
                adapter?.removeData(view.tag as String)
            }
            recyclerView_main.adapter = adapter
            recyclerView_main.layoutManager = LinearLayoutManager(this)
        }
        adapter?.updateData(data!! as ArrayList<WeatherForecast>)
        progressBar.visibility = View.GONE

        this.toast("hello")

    }

    override fun onLoaderReset(loader: Loader<List<WeatherForecast>>) {
    }
}
