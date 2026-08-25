package com.mindtoscreen.cappupos.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mindtoscreen.cappupos.data.dao.*
import com.mindtoscreen.cappupos.data.entities.*

@Database(
    entities = [
        ProductEntity::class,
        CategoryEntity::class,
        OrderEntity::class,
        OrderDetailEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao
    abstract fun orderDetailDao(): OrderDetailDao

    companion object {
        val MIGRATION_1_2 = MigrationV1ToV2()
    }
}
