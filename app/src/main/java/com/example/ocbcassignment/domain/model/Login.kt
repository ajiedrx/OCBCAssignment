package com.example.ocbcassignment.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Login(
    @field:SerializedName("accountNo")
    val accountNo: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("username")
    val username: String,
) : Parcelable
