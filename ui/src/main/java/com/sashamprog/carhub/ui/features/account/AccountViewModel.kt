package com.sashamprog.carhub.ui.features.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sashamprog.carhub.domain.model.User
import com.sashamprog.carhub.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow(User("", ""))
    val user: StateFlow<User> = _user

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            userRepository.getUser().collect { user ->
                _user.value = user
            }
        }
    }

    fun updateAvatar(avatarUrl: String) {
        viewModelScope.launch {
            userRepository.updateAvatar(avatarUrl).collect {
                _user.value = _user.value.copy(avatarUrl = avatarUrl) // Update user in state
            }
        }
    }

    fun updateNickname(nickname: String) {
        viewModelScope.launch {
            userRepository.updateNickname(nickname).collect {
                _user.value = _user.value.copy(nickname = nickname) // Update user in state
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}
