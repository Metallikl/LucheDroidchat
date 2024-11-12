package com.dluche.luchedroidchat.data.network.di

import com.dluche.luchedroidchat.data.network.NetworkDataSource
import com.dluche.luchedroidchat.data.network.NetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkDataSourceModule {

    @Binds
    @Singleton
    fun bindNetworkDataSource(networkDataSource: NetworkDataSourceImpl): NetworkDataSource
}