package com.sashamprog.carhub.data.repository

import android.content.SharedPreferences
import com.sashamprog.carhub.data.source.UserDataSource
import com.sashamprog.carhub.domain.model.AuthResult
import com.sashamprog.carhub.domain.model.User
import com.sashamprog.carhub.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class UserRepositoryImpl(
    private val userDataSource: UserDataSource,
    private val sharedPreferences: SharedPreferences
) : UserRepository {
    override fun isLoggedIn(): Boolean = sharedPreferences.getBoolean("isLoggedIn", false)

    override fun login(email: String, password: String): Flow<AuthResult> {
        return userDataSource.login(email, password).onEach {
            if (it is AuthResult.Success) {
                sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
            }
        }
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
        sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
    }
}
