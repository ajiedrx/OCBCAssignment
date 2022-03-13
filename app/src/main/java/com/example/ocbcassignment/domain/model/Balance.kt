package com.example.ocbcassignment.domain.model

import com.google.gson.annotations.SerializedName

data class Balance(

    @field:SerializedName("balance")
    val balance: Double? = null,

    @field:SerializedName("accountNo")
    val accountNo: String? = null,

    @field:SerializedName("status")
    val status: String? = null,
)
