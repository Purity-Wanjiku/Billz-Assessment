package com.collections.billz.repository

import com.collections.billz.api.ApiClient
import com.collections.billz.api.ApiInterface
import com.collections.billz.models.LoginRequest
import com.collections.billz.models.LoginResponse
import com.collections.billz.models.RegisterRequest
import com.collections.billz.models.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginUserRepository {
    var client = ApiClient.buildApiClient(ApiInterface::class.java)

    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse> {
        return withContext(Dispatchers.IO) {
            client.loginUser(loginRequest)
        }
    }
}


