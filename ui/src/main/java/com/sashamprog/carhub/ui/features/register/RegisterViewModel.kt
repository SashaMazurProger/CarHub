package com.sashamprog.carhub.ui.features.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sashamprog.carhub.domain.model.AuthResult
import com.sashamprog.carhub.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class RegisterViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Idle)
    val registrationState = _registrationState.asStateFlow()

    fun register(email: String, password: String, repeatPassword: String) {
        if (email.isBlank() || password.isBlank() || repeatPassword.isBlank()) {
            _registrationState.value = RegistrationState.Error("Fields cannot be empty")
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _registrationState.value = RegistrationState.Error("Invalid email format")
            return
        }

        if (password != repeatPassword) {
            _registrationState.value = RegistrationState.Error("Passwords do not match")
            return
        }

        viewModelScope.launch {
            loginUseCase.register(email, password)
                .catch {
                    _registrationState.value =
                        RegistrationState.Error(it.message ?: "Error while registration")
                }
                .collect {
                    if (it is AuthResult.Success) {
                        _registrationState.value = RegistrationState.Success
                    } else if (it is AuthResult.Error) {
                        _registrationState.value =
                            RegistrationState.Error(it.message)
                    }
                }
        }
    }
}

sealed class RegistrationState {
    object Idle : RegistrationState()
    object Success : RegistrationState()
    data class Error(val message: String) : RegistrationState()
}
