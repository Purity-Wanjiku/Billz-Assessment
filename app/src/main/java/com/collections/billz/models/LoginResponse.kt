package com.collections.billz.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    var message : String,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("user_id")
    val  userId:String
)
