package com.example.ilsapp.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun toJsonfromArraylist(value: ArrayList<Int>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromJsonToArraylist(value: String): ArrayList<Int> {
        val typeToken = object : TypeToken<ArrayList<Integer>>() {}.type
        return try {
            Gson().fromJson(value, typeToken)
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}