package com.sashamprog.carhub.ui.features.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sashamprog.carhub.domain.model.User
import com.sashamprog.carhub.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow(User("", ""))
    val user: StateFlow<User> = _user

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            userRepository.getUser()
                .catch { e ->
                    // Handle error appropriately, e.g., show an error message
                }
                .collect { user ->
                    _user.value = user
                }
        }
    }

    fun updateAvatar(avatarUrl: String) {
        viewModelScope.launch {
            userRepository.updateAvatar(avatarUrl)
                .catch { e ->
                    // Handle error, e.g., show error message
                }
                .collect {
                    _user.value = _user.value.copy(avatarUrl = avatarUrl) // Update user in state
                }
        }
    }

    fun updateNickname(nickname: String) {
        viewModelScope.launch {
            userRepository.updateNickname(nickname)
                .catch { e ->
                    // Handle error, e.g., show error message
                }
                .collect {
                    _user.value = _user.value.copy(nickname = nickname) // Update user in state
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
            // Perform logout logic here, e.g., clear session
        }
    }
}
