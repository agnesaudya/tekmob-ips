package com.example.ilsapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ilsapp.entity.Fingerprint

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
}