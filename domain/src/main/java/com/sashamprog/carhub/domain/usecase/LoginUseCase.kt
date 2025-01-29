package com.sashamprog.carhub.domain.usecase

import com.sashamprog.carhub.domain.model.AuthResult
import com.sashamprog.carhub.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val userRepository: UserRepository) {
    fun login(email: String, password: String): Flow<AuthResult> {
        return userRepository.login(email, password)
    }
}