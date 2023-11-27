package com.example.ilsapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Fingerprint(
    @PrimaryKey val label: String,
    @ColumnInfo(name = "row") val row: Int,
    @ColumnInfo(name = "col") val col: Int,
    @ColumnInfo(name = "rssi") var rssi: ArrayList<Int>,
)