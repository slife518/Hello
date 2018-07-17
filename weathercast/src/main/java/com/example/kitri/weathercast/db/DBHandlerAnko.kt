package com.example.kitri.weathercast.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.kitri.weathercast.data.City

import org.jetbrains.anko.db.*

class DBHandlerAnko(context: Context)
        : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object CityTable {
        val DB_NAME: String = "city.db"
        val DB_VERSION: Int = 1

        val TABLE_NAME: String = "city"
        val ID: String = "_id"
        val NAME: String = "name"
        val API_ID: String = "api_id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(CityTable.TABLE_NAME, true,
                Pair(CityTable.ID, INTEGER + PRIMARY_KEY + AUTOINCREMENT),
                Pair(CityTable.NAME, TEXT),
                Pair(CityTable.API_ID, TEXT))
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun getCityDataAll(): List<City> {
        readableDatabase.use {
            val data = ArrayList<City>()
            val cursor = it.query(CityTable.TABLE_NAME,
                    arrayOf(CityTable.ID, CityTable.NAME, CityTable.API_ID),
                    null, null, null, null, null)
            if ( cursor.count == 0 ) return data
            cursor.moveToFirst()
            do {
                val city = City(cursor.getString(2), cursor.getString(1))
                data.add(city)
            } while( cursor.moveToNext())
            return data
        }
    }

    fun saveCity(city: City): Unit {
        writableDatabase.use {
            it.insert(CityTable.TABLE_NAME, null, ContentValues().apply {
                put(CityTable.NAME, city.name)
                put(CityTable.API_ID, city.id)
            })
        }
    }

    fun deleteCity(id: String): Unit {
        writableDatabase.use {
            it.execSQL("DELETE FROM ${CityTable.TABLE_NAME} WHERE ${CityTable.API_ID} = ${id};")
        }
    }

}
