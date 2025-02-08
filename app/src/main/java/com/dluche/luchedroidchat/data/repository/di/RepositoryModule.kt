package com.dluche.luchedroidchat.data.repository.di

import com.dluche.luchedroidchat.data.repository.AuthRepository
import com.dluche.luchedroidchat.data.repository.AuthRepositoryImpl
import com.dluche.luchedroidchat.data.repository.SettingsPreferenceRepository
import com.dluche.luchedroidchat.data.repository.SettingsPreferenceRepositoryImpl
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
    fun bindsSettingsPreferenceRepository(repository: SettingsPreferenceRepositoryImpl): SettingsPreferenceRepository

}