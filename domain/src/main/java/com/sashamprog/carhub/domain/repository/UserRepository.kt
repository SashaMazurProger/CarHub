package com.sashamprog.carhub.domain.repository

import com.sashamprog.carhub.domain.model.AuthResult
import com.sashamprog.carhub.domain.model.User
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    fun login(email: String, password: String): Flow<AuthResult>
    suspend fun getUser(): Flow<User>
    suspend fun updateAvatar(newAvatarUrl: String): Flow<Unit>
    suspend fun updateNickname(newNickname: String): Flow<Unit>
    suspend fun logout()
    fun isLoggedIn(): Boolean
}