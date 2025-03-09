package com.dluche.luchedroidchat.data.database.di

import android.content.Context
import androidx.room.Room
import com.dluche.luchedroidchat.data.database.LucheDroidChatDatabase
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
    fun provideDatabase(
        @ApplicationContext applicationContext: Context
    ): LucheDroidChatDatabase = Room.databaseBuilder(
            applicationContext,
            LucheDroidChatDatabase::class.java,
            "droidchat_db"
        ).build()

}