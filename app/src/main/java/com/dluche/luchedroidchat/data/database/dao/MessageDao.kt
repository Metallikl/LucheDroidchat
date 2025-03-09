package com.dluche.luchedroidchat.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dluche.luchedroidchat.data.database.entity.MessageEntity

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages WHERE receiver_id = :receiverId ORDER BY timestamp DESC")
    suspend fun getPagedMessages(receiverId: Int): PagingSource<Int, MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<MessageEntity>)

    @Query("DELETE FROM messages WHERE receiver_id = :receiverId")
    suspend fun clearMessages(receiverId: Int)

}