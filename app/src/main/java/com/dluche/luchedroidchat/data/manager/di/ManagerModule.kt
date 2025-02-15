package com.dluche.luchedroidchat.data.manager.di

import com.dluche.luchedroidchat.data.manager.selfuser.SelfUserManager
import com.dluche.luchedroidchat.data.manager.selfuser.SelfUserManagerImpl
import com.dluche.luchedroidchat.data.manager.token.SecureTokenManagerImpl
import com.dluche.luchedroidchat.data.manager.token.TokenManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ManagerModule {

    @Binds
    @Singleton
    fun bindTokenManager(tokenManager: SecureTokenManagerImpl): TokenManager

    @Binds
    @Singleton
    fun bindSelfUserManager(selfUserManager: SelfUserManagerImpl): SelfUserManager
}