package com.example.ilsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ilsapp.dao.FingerprintDao
import com.example.ilsapp.entity.Fingerprint
import com.example.ilsapp.utils.Converters

@Database(entities = [Fingerprint::class], version = 1)
@TypeConverters(Converters::class)
abstract class FingerprintDatabase : RoomDatabase() {
    abstract fun fingerprintDao(): FingerprintDao
}