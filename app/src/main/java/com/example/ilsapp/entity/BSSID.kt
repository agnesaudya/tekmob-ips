package com.example.ilsapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BSSID (
    @PrimaryKey(autoGenerate = true) val uid:Int,
    @ColumnInfo(name = "BSSID") val bssid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "SSID") val ssid: String,
)