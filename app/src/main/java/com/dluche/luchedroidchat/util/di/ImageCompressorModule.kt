package com.dluche.luchedroidchat.util.di

import com.dluche.luchedroidchat.util.image.ImageCompressor
import com.dluche.luchedroidchat.util.image.ImageCompressorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)

interface ImageCompressorModule {

    @Binds
    fun bindImageCompressor(compressor: ImageCompressorImpl): ImageCompressor


}