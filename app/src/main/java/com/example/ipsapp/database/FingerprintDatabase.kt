package com.example.ipsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ipsapp.dao.FingerprintDao
import com.example.ipsapp.entity.BSSID
import com.example.ipsapp.entity.Fingerprint
import com.example.ipsapp.utils.Converters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Fingerprint::class], version = 1)
@TypeConverters(Converters::class)
abstract class FingerprintDatabase : RoomDatabase() {
    abstract fun fingerprintDao(): FingerprintDao

    companion object {
        @Volatile
        private var INSTANCE: FingerprintDatabase? = null

        fun getInstance(context: Context): FingerprintDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FingerprintDatabase::class.java,
                    "fingerprint"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            INSTANCE?.let { database ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    val fingerprintdDao = database.fingerprintDao()

                                    // Read data from the JSON file
                                    val jsonString = context.assets.open("bssid.json")
                                        .bufferedReader().use { it.readText() }

                                    // Convert JSON to a list of BSSID objects
                                    val prepopulatedFingerprints =
                                        Gson().fromJson<List<Fingerprint>>(
                                            jsonString,
                                            object : TypeToken<List<Fingerprint>>() {}.type
                                        )


                                    fingerprintdDao.insertAll(prepopulatedFingerprints)
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}