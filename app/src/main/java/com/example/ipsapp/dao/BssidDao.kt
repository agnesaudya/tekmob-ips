package com.example.ipsapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ipsapp.entity.BSSID


@Dao
interface BssidDao {
    @Query("SELECT * FROM bssid")
    fun getAll(): List<BSSID>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(bssids: List<BSSID>)
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