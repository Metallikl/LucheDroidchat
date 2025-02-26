package com.dluche.luchedroidchat.data.pagingsource.di

import androidx.paging.PagingSource
import com.dluche.luchedroidchat.data.network.NetworkDataSource
import com.dluche.luchedroidchat.data.pagingsource.UserPagingSource
import com.dluche.luchedroidchat.model.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PagingSourceModule {

    @Provides
    @Singleton
    fun provideUserPagingSource(
        networkDataSource: NetworkDataSource
    ): PagingSource<Int, User> =UserPagingSource(networkDataSource)
}