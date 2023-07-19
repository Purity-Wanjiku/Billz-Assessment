package com.collections.billz.models

import com.google.gson.annotations.SerializedName

data class LoginUser(
    @SerializedName("user_id") var userId : String,
    var email : String
    )
