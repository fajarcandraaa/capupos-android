package com.mindtoscreen.cappupos.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.mindtoscreen.cappupos.data.AppDatabase
import com.mindtoscreen.cappupos.data.dao.CategoryDao
import com.mindtoscreen.cappupos.data.dao.OrderDao
import com.mindtoscreen.cappupos.data.dao.OrderDetailDao
import com.mindtoscreen.cappupos.data.dao.ProductDao
import com.mindtoscreen.cappupos.data.dao.StockHistoryDao
import com.mindtoscreen.cappupos.data.dao.StoreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
            .addMigrations(
                AppDatabase.MIGRATION_1_2,
                AppDatabase.MIGRATION_2_3,
                AppDatabase.MIGRATION_3_4,
                AppDatabase.MIGRATION_4_5,
                AppDatabase.MIGRATION_5_6
            )
            .addCallback(AppDatabase.CALLBACK)
            .build()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(database: AppDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    @Singleton
    fun provideOrderDao(database: AppDatabase): OrderDao {
        return database.orderDao()
    }

    @Provides
    @Singleton
    fun provideOrderDetailDao(database: AppDatabase): OrderDetailDao {
        return database.orderDetailDao()
    }

    @Provides
    @Singleton
    fun provideStockHistoryDao(database: AppDatabase): StockHistoryDao {
        return database.stockHistoryDao()
    }

    @Provides
    @Singleton
    fun provideStoreDao(database: AppDatabase): StoreDao {
        return database.storeDao()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("cappu_prefs", Context.MODE_PRIVATE)
    }
}