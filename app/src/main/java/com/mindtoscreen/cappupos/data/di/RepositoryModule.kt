package com.mindtoscreen.cappupos.data.di

import com.mindtoscreen.cappupos.data.repository.CategoryRepositoryImpl
import com.mindtoscreen.cappupos.data.repository.OrderRepositoryImpl
import com.mindtoscreen.cappupos.data.repository.ProductRepositoryImpl
import com.mindtoscreen.cappupos.domain.repository.CategoryRepository
import com.mindtoscreen.cappupos.domain.repository.OrderRepository
import com.mindtoscreen.cappupos.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl
    ): OrderRepository
}
