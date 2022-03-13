package com.example.ocbcassignment.data.model.auth.response

import android.os.Parcelable
import com.example.ocbcassignment.domain.model.Register
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("token")
    val token: String,
) : Parcelable {
    fun mapToDomain(): Register {
        return Register(
            status, token
        )
    }
}
