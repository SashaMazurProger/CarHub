package com.sashamprog.carhub.data.source

import com.sashamprog.carhub.domain.model.AuthResult
import com.sashamprog.carhub.domain.model.Car
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CarHubApi {
    @GET("login")
    fun login(email: String, password: String): Response<AuthResult>

    @GET("user")
    suspend fun getUser(): Response<UserResponse>

    @PUT("user/avatar")
    suspend fun updateAvatar(
        @Body avatarRequest: AvatarRequest
    ): Response<Unit>

    @PUT("user/{userId}/nickname")
    suspend fun updateNickname(
        @Body nicknameRequest: NicknameRequest
    ): Response<Unit>



    @GET("cars")
    suspend fun getCars(): Response<List<Car>>

    @GET("cars/{carId}")
    suspend fun getCar(@Path("carId") carId: String): Response<Car>

    @POST("cars")
    suspend fun addCar(@Body car: Car): Response<Car>

    @PUT("cars/{carId}")
    suspend fun updateCar(
        @Path("carId") carId: String,
        @Body car: Car
    ): Response<Car>

    @DELETE("cars/{carId}")
    suspend fun deleteCar(@Path("carId") carId: String): Response<Unit>
}

inline fun <T> handleResponse(response: Response<T>): Flow<T> = flow {
    if (response.isSuccessful) {
        response.body()?.let { emit(it) } ?: throw Exception("Empty response body")
    } else {
        throw Exception("API request failed: ${response.code()}")
    }
}

data class UserResponse(
    val avatarUrl: String,
    val nickname: String
)

data class AvatarRequest(val avatarUrl: String)
data class NicknameRequest(val nickname: String)