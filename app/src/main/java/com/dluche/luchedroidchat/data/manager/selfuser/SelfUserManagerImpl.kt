package com.dluche.luchedroidchat.data.manager.selfuser

import android.content.Context
import com.dluche.luchedroidchat.SelfUser
import com.dluche.luchedroidchat.data.IoDispatcher
import com.dluche.luchedroidchat.data.datastore.selfUserStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SelfUserManagerImpl @Inject constructor(
    @ApplicationContext context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) :  SelfUserManager {

    private val selfUserStore = context.selfUserStore

    override val selfUserFlow: Flow<SelfUser>
        get() = selfUserStore.data

    override suspend fun saveSelfUserData(
        id: Int,
        firstName: String,
        lastName: String,
        profilePictureUrl: String,
        username: String
    ) {
        withContext(ioDispatcher) {
            selfUserStore.updateData {
                it.toBuilder()
                    .setId(id)
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setProfilePictureUrl(profilePictureUrl)
                    .setUsername(username)
                    .build()
            }
        }
    }

    override suspend fun clearSelfUser() {
        withContext(ioDispatcher) {
            selfUserStore.updateData {
                it.toBuilder()
                    .clearId()
                    .clearFirstName()
                    .clearLastName()
                    .clearProfilePictureUrl()
                    .clearUsername()
                    .build()
            }
        }
    }
}