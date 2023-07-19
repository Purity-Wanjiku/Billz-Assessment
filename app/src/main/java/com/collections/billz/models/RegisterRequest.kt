package com.collections.billz.models

import com.google.gson.annotations.SerializedName

data class RegisterRequest(

    @SerializedName("phone_number")val phoneNumber : String,
    @SerializedName("first_name")val firstName : String,
    @SerializedName("last_name")val lastName : String,
    var email: String,
    var password : String
)
