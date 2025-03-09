package com.dluche.luchedroidchat.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dluche.luchedroidchat.data.database.entity.MessageRemoteKeyEntity

@Dao
interface MessageRemoteKeyDao {
    @Query("SELECT * FROM message_remote_keys WHERE receiver_id = :receiverId")
    fun getRemoteKey(receiverId: Int): MessageRemoteKeyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKey(remoteKey: MessageRemoteKeyEntity)

    @Query("DELETE FROM message_remote_keys WHERE receiver_id = :receiverId")
    suspend fun clearMessageRemoteKeys(receiverId: Int)

}