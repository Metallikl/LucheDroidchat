package com.dluche.luchedroidchat.data.repository.di

import com.dluche.luchedroidchat.data.repository.AuthRepository
import com.dluche.luchedroidchat.data.repository.AuthRepositoryImpl
import com.dluche.luchedroidchat.data.repository.ChatRepository
import com.dluche.luchedroidchat.data.repository.ChatRepositoryImpl
import com.dluche.luchedroidchat.data.repository.UserRepository
import com.dluche.luchedroidchat.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindsChatRepository(repository: ChatRepositoryImpl): ChatRepository

    @Binds
    fun bindsUserRepository(repository: UserRepositoryImpl): UserRepository
}