package com.sashamprog.carhub.data.source

import com.sashamprog.carhub.domain.model.AuthResult
import com.sashamprog.carhub.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    fun login(email: String, password: String): Flow<AuthResult>
    suspend fun getUser(): Flow<User>
    suspend fun updateAvatar(avatarUrl: String): Flow<Unit>
    suspend fun updateNickname(nickname: String): Flow<Unit>
    suspend fun logout()
}
