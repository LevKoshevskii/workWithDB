package com.example.workWithDB.util

import com.example.workWithDB.models.Person
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JsonReader {
    fun getPersons(json:String): List<Person>{
        return Gson().fromJson(json, object : TypeToken<List<Person?>?>(){}.getType())
    }
}