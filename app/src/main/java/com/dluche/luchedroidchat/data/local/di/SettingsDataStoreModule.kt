package com.dluche.luchedroidchat.data.local.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dluche.luchedroidchat.data.local.di.SettingsDataStoreModule.Companion.DATA_STORE_SETTINGS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_SETTINGS)

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsDataStoreModule {

    @Provides
    @Singleton
    fun providerSettingsDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.settingsDataStore
    }

    companion object {
        const val DATA_STORE_SETTINGS = "settings"
    }
}