package com.dluche.luchedroidchat

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

class LucheDroidChatFileProvider : FileProvider(R.xml.file_paths) {
    companion object {
        const val AUTHORITY = "com.dluche.luchedroidchat.fileprovider"

        fun getImageUri(context: Context): Uri {
            val tempFile = File.createTempFile("profile_image", ".jpg", context.cacheDir)
            val authority = "${context.packageName}.fileprovider"

            return getUriForFile(
                context,
                authority,
                tempFile
            )
        }
    }
}