package com.mindtoscreen.cappupos.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mindtoscreen.cappupos.data.dao.*
import com.mindtoscreen.cappupos.data.entities.*

@Database(
    entities = [
        ProductEntity::class,
        CategoryEntity::class,
        CustomerEntity::class,
        OrderEntity::class,
        OrderDetailEntity::class,
        PaymentEntity::class,
        EmployeeEntity::class,
        StoreEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun customerDao(): CustomerDao
    abstract fun orderDao(): OrderDao
    abstract fun orderDetailDao(): OrderDetailDao
    abstract fun paymentDao(): PaymentDao
    abstract fun employeeDao(): EmployeeDao
    abstract fun storeDao(): StoreDao
}
