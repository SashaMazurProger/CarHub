package com.sashamprog.carhub.data.repository

import com.sashamprog.carhub.data.api.CarHubApiImpl
import com.sashamprog.carhub.domain.model.AuthResult
import com.sashamprog.carhub.domain.model.User
import com.sashamprog.carhub.domain.repository.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(private val carHubApi: CarHubApiImpl) : UserRepository {

    override fun login(email: String, password: String): Flow<AuthResult> = flow {
        delay(2000) //todo Імітація мережевого запиту

        if (email == "test" && password == "test") {
            emit(AuthResult.Success)
        } else {
            emit(AuthResult.Error("Invalid credentials"))
        }
    }

    override fun getUser(): Flow<User> = carHubApi.getUser()

    override suspend fun updateAvatar(avatarUrl: String): Flow<Unit> =
        carHubApi.updateAvatar(avatarUrl)

    override suspend fun updateNickname(nickname: String): Flow<Unit> =
        carHubApi.updateNickname(nickname)

    override suspend fun logout() {
        // Handle logout (e.g., clear session, tokens, etc.)
    }
}