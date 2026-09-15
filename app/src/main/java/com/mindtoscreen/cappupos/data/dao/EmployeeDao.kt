package com.mindtoscreen.cappupos.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mindtoscreen.cappupos.data.entities.EmployeeEntity

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employees")
    suspend fun getAll(): List<EmployeeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(employees: List<EmployeeEntity>)
}
