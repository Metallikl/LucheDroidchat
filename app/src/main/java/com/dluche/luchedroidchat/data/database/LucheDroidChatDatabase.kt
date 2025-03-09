package com.dluche.luchedroidchat.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dluche.luchedroidchat.data.database.dao.MessageDao
import com.dluche.luchedroidchat.data.database.dao.MessageRemoteKeyDao
import com.dluche.luchedroidchat.data.database.entity.MessageEntity
import com.dluche.luchedroidchat.data.database.entity.MessageRemoteKeyEntity


@Database(
    entities = [
        MessageEntity::class,
        MessageRemoteKeyEntity::class
    ],
    version = 1

)
abstract class LucheDroidChatDatabase: RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun messageRemoteKeyDao(): MessageRemoteKeyDao
}