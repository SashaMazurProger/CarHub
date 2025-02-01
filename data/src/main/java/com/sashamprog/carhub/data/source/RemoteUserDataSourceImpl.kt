package com.sashamprog.carhub.data.source

import com.sashamprog.carhub.domain.model.AuthResult
import com.sashamprog.carhub.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RemoteUserDataSourceImpl(private val api: CarHubApi) : UserDataSource {

    override suspend fun getUser(): Flow<User> =
        handleResponse(api.getUser()).map { User(it.avatarUrl, it.nickname) }

    override suspend fun updateAvatar(avatarUrl: String): Flow<Unit> =
        handleResponse(api.updateAvatar(AvatarRequest(avatarUrl)))

    override suspend fun updateNickname(nickname: String): Flow<Unit> = handleResponse(
        api.updateNickname(NicknameRequest(nickname))
    )

    override fun login(email: String, password: String): Flow<AuthResult> =
        handleResponse(api.login(email, password))

    override fun register(email: String, password: String): Flow<AuthResult> =
        handleResponse(api.register(email, password))

    override suspend fun logout() {
        //
    }
}
