package com.mindtoscreen.cappupos.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mindtoscreen.cappupos.data.entities.CustomerEntity

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customers")
    suspend fun getAll(): List<CustomerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(customers: List<CustomerEntity>)
}
