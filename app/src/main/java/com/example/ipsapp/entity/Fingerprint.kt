package com.example.ipsapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Fingerprint(
    @PrimaryKey(autoGenerate = true) var uid: Int,

    @ColumnInfo(name = "label") var label: String,

    @ColumnInfo(name = "bssid1_rssi") var bssid1_rssi: Double = 0.0,
    @ColumnInfo(name = "bssid2_rssi") var bssid2_rssi: Double = 0.0,
    @ColumnInfo(name = "bssid3_rssi") var bssid3_rssi: Double = 0.0,
    @ColumnInfo(name = "bssid4_rssi") var bssid4_rssi: Double = 0.0,
    @ColumnInfo(name = "bssid5_rssi") var bssid5_rssi: Double = 0.0,
    @ColumnInfo(name = "bssid6_rssi") var bssid6_rssi: Double = 0.0,
    @ColumnInfo(name = "bssid7_rssi") var bssid7_rssi: Double = 0.0,
    @ColumnInfo(name = "bssid8_rssi") var bssid8_rssi: Double = 0.0,
    @ColumnInfo(name = "bssid9_rssi") var bssid9_rssi: Double = 0.0,
    @ColumnInfo(name = "bssid10_rssi") var bssid10_rssi: Double = 0.0,
    @ColumnInfo(name = "bssid11_rssi") var bssid11_rssi: Double = 0.0,
    @ColumnInfo(name = "bssid12_rssi") var bssid12_rssi: Double = 0.0,
    @ColumnInfo(name = "bssid13_rssi") var bssid13_rssi: Double = 0.0,
    @ColumnInfo(name = "bssid14_rssi") var bssid14_rssi: Double = 0.0,
    @ColumnInfo(name = "bssid15_rssi") var bssid15_rssi: Double = 0.0,
    @ColumnInfo(name = "bssid16_rssi") var bssid16_rssi: Double = 0.0,
    @ColumnInfo(name = "bssid17_rssi") var bssid17_rssi: Double = 0.0,
    @ColumnInfo(name = "bssid18_rssi") var bssid18_rssi: Double = 0.0,
    @ColumnInfo(name = "bssid19_rssi") var bssid19_rssi: Double = 0.0,
    @ColumnInfo(name = "bssid20_rssi") var bssid20_rssi: Double = 0.0,


) {
    // Primary constructor
    constructor() : this(0, "RxCx")
}