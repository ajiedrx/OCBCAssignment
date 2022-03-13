package com.example.ocbcassignment.data.model.feature.response

import com.example.ocbcassignment.domain.model.Receipient
import com.google.gson.annotations.SerializedName

data class Receipient(

    @field:SerializedName("accountHolder")
    val accountHolder: String? = "",

    @field:SerializedName("accountNo")
    val accountNo: String? = "",
) {
    fun mapToDomain(): Receipient {
        return Receipient(
            accountHolder, accountNo
        )
    }
}