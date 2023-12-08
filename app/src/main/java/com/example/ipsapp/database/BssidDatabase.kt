package com.example.ipsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ipsapp.dao.BssidDao
import com.example.ipsapp.entity.BSSID
import com.example.ipsapp.utils.BSSIDTypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [BSSID::class], version = 1)
@TypeConverters(BSSIDTypeConverter::class)
abstract class BssidDatabase : RoomDatabase() {
    abstract fun bssidDao(): BssidDao

    companion object {
        @Volatile
        private var INSTANCE: BssidDatabase? = null

        fun getInstance(context: Context): BssidDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BssidDatabase::class.java,
                    "bssid"
                )
//                    .addCallback(object : Callback() {
//                        override fun onCreate(db: SupportSQLiteDatabase) {
//                            super.onCreate(db)
//                            INSTANCE?.let { database ->
//                                CoroutineScope(Dispatchers.IO).launch {
//                                    val bssidDao = database.bssidDao()
//
//                                    // Read data from the JSON file
//                                    val jsonString = context.assets.open("bssid.json")
//                                        .bufferedReader().use { it.readText() }
//
//                                    // Convert JSON to a list of BSSID objects
//                                    val prepopulatedBSSIDs = Gson().fromJson<List<BSSID>>(
//                                        jsonString,
//                                        object : TypeToken<List<BSSID>>() {}.type
//                                    )
//
//
//                                    bssidDao.insertAll(prepopulatedBSSIDs)
//                                }
//                            }
//                        }
//                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
