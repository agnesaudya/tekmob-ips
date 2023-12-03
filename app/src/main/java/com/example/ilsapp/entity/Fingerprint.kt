package com.example.ilsapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Fingerprint(
    @PrimaryKey(autoGenerate = true) val uid: Int,

    @ColumnInfo(name = "label") var label: String,

    @ColumnInfo(name = "bssid1_rssi") var bssid1_rssi: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "bssid2_rssi") var bssid2_rssi: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "bssid3_rssi") var bssid3_rssi: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "bssid4_rssi") var bssid4_rssi: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "bssid5_rssi") var bssid5_rssi: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "bssid6_rssi") var bssid6_rssi: Int = Int.MAX_VALUE
) {
    // Primary constructor
    constructor() : this(0, "dummy label")
}