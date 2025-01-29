package com.sashamprog.carhub.ui.features.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sashamprog.carhub.data.repository.UserRepositoryImpl
import com.sashamprog.carhub.domain.model.AuthResult
import com.sashamprog.carhub.domain.repository.UserRepository
import com.sashamprog.carhub.domain.usecase.LoginUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(private val loginUseCase: LoginUseCase) :
    ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Please fill in both fields")
            return
        }

        _authState.value = AuthState.Loading

        // Simulate network delay
        viewModelScope.launch {
            delay(2000) //todo
            loginUseCase.login(email, password).collect {
                if (it is AuthResult.Success) {
                    _authState.value = AuthState.Success
                } else {
                    _authState.value = AuthState.Error("Invalid credentials")
                }
            }
        }
    }
}
