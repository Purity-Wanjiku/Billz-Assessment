package com.collections.billz.models

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("first_name") var firstname : String,
    @SerializedName("last_name") var lastname : String,
    var email : String,
    var password : String,
    @SerializedName("phone_number") var phoneNumber : String
)
