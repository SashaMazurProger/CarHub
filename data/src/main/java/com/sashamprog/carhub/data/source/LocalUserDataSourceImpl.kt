package com.sashamprog.carhub.data.source

import android.content.SharedPreferences
import com.sashamprog.carhub.domain.model.AuthResult
import com.sashamprog.carhub.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalUserDataSourceImpl(private val sharedPreferences: SharedPreferences) : UserDataSource {

    private val AVATAR_URL_KEY = "avatar_url"
    private val NICKNAME_KEY = "nickname"

    override fun login(email: String, password: String): Flow<AuthResult> = flow {
        delay(2000)
        if (email == "test@mail.com" && password == "test") {
            sharedPreferences.edit().putString(NICKNAME_KEY, email).apply()
            emit(AuthResult.Success("token"))
        } else {
            emit(AuthResult.Error("Invalid credentials"))
        }
    }

    override fun register(email: String, password: String): Flow<AuthResult> = flow {
        delay(2000)
        sharedPreferences.edit().putString(NICKNAME_KEY, email).apply()
        emit(AuthResult.Success("token"))
    }

    override suspend fun getUser(): Flow<User> = flow {
        val avatarUrl = sharedPreferences.getString(AVATAR_URL_KEY, "") ?: ""
        val nickname = sharedPreferences.getString(NICKNAME_KEY, "test@mail.com") ?: "test@mail.com"
        emit(User(avatarUrl, nickname))
    }

    override suspend fun updateAvatar(avatarUrl: String): Flow<Unit> = flow {
        sharedPreferences.edit().putString(AVATAR_URL_KEY, avatarUrl).apply()
        emit(Unit)
    }

    override suspend fun updateNickname(nickname: String): Flow<Unit> = flow {
        sharedPreferences.edit().putString(NICKNAME_KEY, nickname).apply()
        emit(Unit)
    }

    override suspend fun logout() {
        sharedPreferences.edit().clear().apply()
    }
}
