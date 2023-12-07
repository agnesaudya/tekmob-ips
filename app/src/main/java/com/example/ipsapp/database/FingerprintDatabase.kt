package com.example.ipsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ipsapp.dao.FingerprintDao
import com.example.ipsapp.entity.Fingerprint
import com.example.ipsapp.utils.Converters

@Database(entities = [Fingerprint::class], version = 1)
@TypeConverters(Converters::class)
abstract class FingerprintDatabase : RoomDatabase() {
    abstract fun fingerprintDao(): FingerprintDao
}