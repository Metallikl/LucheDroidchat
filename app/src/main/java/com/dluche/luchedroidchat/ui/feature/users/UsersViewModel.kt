package com.dluche.luchedroidchat.ui.feature.users

import androidx.lifecycle.ViewModel
import com.dluche.luchedroidchat.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    val users = userRepository.getUsers()

}