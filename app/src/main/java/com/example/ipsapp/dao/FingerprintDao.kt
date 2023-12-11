package com.example.ipsapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ipsapp.entity.BSSID
import com.example.ipsapp.entity.Fingerprint

@Dao
interface FingerprintDao {
    @Query("SELECT * FROM fingerprint")
    fun getAll(): List<Fingerprint>

    @Update
    fun update(fingerprint: Fingerprint)

    @Query("SELECT * FROM fingerprint WHERE label == :label")
    fun findByLabel(label: String): Fingerprint

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg fingerprint: Fingerprint)

    @Delete
    fun delete(fingerprint: Fingerprint)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(fingerprints: List<Fingerprint>)
}