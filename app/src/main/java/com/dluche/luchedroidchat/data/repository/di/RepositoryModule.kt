package com.dluche.luchedroidchat.data.repository.di

import com.dluche.luchedroidchat.data.repository.AuthRepository
import com.dluche.luchedroidchat.data.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsAuthRepository(repository: AuthRepositoryImpl): AuthRepository
}