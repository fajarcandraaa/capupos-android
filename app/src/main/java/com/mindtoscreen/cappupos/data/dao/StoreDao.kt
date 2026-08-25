package com.mindtoscreen.cappupos.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mindtoscreen.cappupos.data.entities.StoreEntity

@Dao
interface StoreDao {
    @Query("SELECT * FROM stores LIMIT 1")
    suspend fun getStore(): StoreEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(store: StoreEntity)
}
