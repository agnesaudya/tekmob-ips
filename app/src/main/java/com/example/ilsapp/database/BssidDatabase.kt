package com.example.ilsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ilsapp.dao.BssidDao
import com.example.ilsapp.dao.FingerprintDao
import com.example.ilsapp.entity.BSSID
import com.example.ilsapp.entity.Fingerprint
import com.example.ilsapp.utils.Converters

@Database(entities = [BSSID::class], version = 1)
abstract class BssidDatabase : RoomDatabase() {
    abstract fun bssidDao(): BssidDao
}