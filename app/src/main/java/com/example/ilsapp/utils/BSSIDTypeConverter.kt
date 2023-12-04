package com.example.ilsapp.utils

import androidx.room.TypeConverter
import com.example.ilsapp.entity.BSSID
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BSSIDTypeConverter {
    @TypeConverter
    fun fromJson(json: String): List<BSSID> {
        val type = object : TypeToken<List<BSSID>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(bssids: List<BSSID>): String {
        return Gson().toJson(bssids)
    }
}