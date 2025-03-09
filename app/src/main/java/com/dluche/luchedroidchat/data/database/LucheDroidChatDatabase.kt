package com.dluche.luchedroidchat.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dluche.luchedroidchat.data.database.dao.MessageDao
import com.dluche.luchedroidchat.data.database.entity.MessageEntity


@Database(
    entities = [
        MessageEntity::class,
    ],
    version = 1

)
abstract class LucheDroidChatDatabase: RoomDatabase() {
    abstract fun messageDao(): MessageDao
}