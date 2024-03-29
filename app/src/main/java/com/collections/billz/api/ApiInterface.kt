package com.collections.billz.api

import com.collections.billz.models.LoginRequest
import com.collections.billz.models.LoginResponse
import com.collections.billz.models.RegisterRequest
import com.collections.billz.models.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiInterface {
    @POST("/users/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest) : Response<RegisterResponse>

    @POST("/users/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest) : Response<LoginResponse>
}