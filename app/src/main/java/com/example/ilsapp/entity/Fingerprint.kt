package com.example.ilsapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Fingerprint(
    @PrimaryKey(autoGenerate = true) val uid:Int,

    @ColumnInfo(name = "label") val label: String,

    @ColumnInfo(name = "rssi1") var rssi1: Int,
    @ColumnInfo(name = "rssi2") var rssi2: Int,
    @ColumnInfo(name = "rssi3") var rssi3: Int,
    @ColumnInfo(name = "rssi4") var rssi4: Int,
    @ColumnInfo(name = "rssi5") var rssi5: Int,
    @ColumnInfo(name = "rssi6") var rssi6: Int,


)