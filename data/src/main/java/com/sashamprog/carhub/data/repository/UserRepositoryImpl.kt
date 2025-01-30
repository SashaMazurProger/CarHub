package com.sashamprog.carhub.data.repository

import com.sashamprog.carhub.data.source.UserDataSource
import com.sashamprog.carhub.domain.model.AuthResult
import com.sashamprog.carhub.domain.model.User
import com.sashamprog.carhub.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userDataSource: UserDataSource
) : UserRepository {

    override fun login(email: String, password: String): Flow<AuthResult> {
        return userDataSource.login(email, password)
    }

    override suspend fun getUser(): Flow<User> {
        return userDataSource.getUser()
    }

    override suspend fun updateAvatar(newAvatarUrl: String): Flow<Unit> {
        return userDataSource.updateAvatar(newAvatarUrl)
    }

    override suspend fun updateNickname(newNickname: String): Flow<Unit> {
        return userDataSource.updateNickname(newNickname)
    }

    override suspend fun logout() {
        userDataSource.logout()
    }
}
