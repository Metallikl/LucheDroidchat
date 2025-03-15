package com.dluche.luchedroidchat.data.database.di

import com.dluche.luchedroidchat.data.database.DatabaseDataSource
import com.dluche.luchedroidchat.data.database.DatabaseDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DatabaseDataSourceModule {

    @Binds
    @Singleton
    fun bindDatabaseDataSource(
        databaseDataSource: DatabaseDataSourceImpl
    ): DatabaseDataSource

}