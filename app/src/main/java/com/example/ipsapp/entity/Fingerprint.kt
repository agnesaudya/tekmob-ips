package com.example.ipsapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Fingerprint(
    @PrimaryKey(autoGenerate = true) var uid: Int,

    @ColumnInfo(name = "label") var label: String,

    @ColumnInfo(name = "bssid1_rssi") var bssid1_rssi: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "bssid2_rssi") var bssid2_rssi: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "bssid3_rssi") var bssid3_rssi: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "bssid4_rssi") var bssid4_rssi: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "bssid5_rssi") var bssid5_rssi: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "bssid6_rssi") var bssid6_rssi: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "bssid7_rssi") var bssid7_rssi: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "bssid8_rssi") var bssid8_rssi: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "bssid9_rssi") var bssid9_rssi: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "bssid10_rssi") var bssid10_rssi: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "bssid11_rssi") var bssid11_rssi: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "bssid12_rssi") var bssid12_rssi: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "bssid13_rssi") var bssid13_rssi: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "bssid14_rssi") var bssid14_rssi: Int = Int.MAX_VALUE,

) {
    // Primary constructor
    constructor() : this(0, "dummy label")
}