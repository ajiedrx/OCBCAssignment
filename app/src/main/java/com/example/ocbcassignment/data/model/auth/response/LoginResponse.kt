package com.example.ocbcassignment.data.model.auth.response

import android.os.Parcelable
import com.example.ocbcassignment.domain.model.Login
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(

    @field:SerializedName("accountNo")
    val accountNo: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("username")
    val username: String,
) : Parcelable {
    fun mapToDomain(): Login {
        return Login(
            accountNo, status, token, username
        )
    }
}
