package com.example.ilsapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ilsapp.entity.BSSID


@Dao
interface BssidDao {
    @Query("SELECT * FROM bssid")
    fun getAll(): List<BSSID>

    @Query("SELECT * FROM bssid WHERE bssid == :bssid")
    fun findByBSSID(bssid: String): BSSID

    @Query("SELECT COUNT(*) FROM bssid")
    fun countTotal(): Int

    @Update
    fun update(bssid: BSSID)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg bssid: BSSID)

    @Delete
    fun delete(bssid: BSSID)
}