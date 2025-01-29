package com.sashamprog.carhub.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface CarHubApi {

    @GET("user")
    suspend fun getUser(): UserResponse

    @PUT("user/avatar")
    suspend fun updateAvatar(
        @Body avatarRequest: AvatarRequest
    ): Response<Unit>

    @PUT("user/{userId}/nickname")
    suspend fun updateNickname(
        @Body nicknameRequest: NicknameRequest
    ): Response<Unit>
}

data class UserResponse(
    val avatarUrl: String,
    val nickname: String
)

data class AvatarRequest(val avatarUrl: String)
data class NicknameRequest(val nickname: String)