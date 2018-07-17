package com.example.kitri.weathercast.data

data class City(val id: String,
                val name: String,
                val country: String = "")

data class Cities(var cities: List<City>)