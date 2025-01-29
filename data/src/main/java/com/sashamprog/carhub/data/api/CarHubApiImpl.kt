package com.sashamprog.carhub.data.api

import com.sashamprog.carhub.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CarHubApiImpl constructor(
    private val api: CarHubApi
) {

    // Use Flow to expose API results asynchronously
    fun getUser(): Flow<User> = flow {
        try {
            val response = api.getUser()
            emit(User(response.avatarUrl, response.nickname)) // Emit user data
        } catch (e: Exception) {
            emit(
                User(
                    "",
                    ""
                )
            ) // Emit empty user in case of failure, handle this appropriately in UI
        }
    }

    fun updateAvatar(avatarUrl: String): Flow<Unit> = flow {
        try {
            val response = api.updateAvatar(AvatarRequest(avatarUrl))
            if (response.isSuccessful) {
                emit(Unit) // Emit successful result
            } else {
                throw Exception("Avatar update failed") // Handle error
            }
        } catch (e: Exception) {
            throw Exception("Error updating avatar", e)
        }
    }

    fun updateNickname(nickname: String): Flow<Unit> = flow {
        try {
            val response = api.updateNickname(NicknameRequest(nickname))
            if (response.isSuccessful) {
                emit(Unit) // Emit successful result
            } else {
                throw Exception("Nickname update failed") // Handle error
            }
        } catch (e: Exception) {
            throw Exception("Error updating nickname", e)
        }
    }
}
