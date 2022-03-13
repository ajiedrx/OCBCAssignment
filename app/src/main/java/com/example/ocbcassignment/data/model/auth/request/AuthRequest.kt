package com.example.ocbcassignment.data.model.auth.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthRequest(
    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("username")
    val username: String,
) : Parcelable
