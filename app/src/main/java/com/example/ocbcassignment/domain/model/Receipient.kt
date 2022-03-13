package com.example.ocbcassignment.domain.model

import com.google.gson.annotations.SerializedName

data class Receipient(

    @field:SerializedName("accountHolder")
    val accountHolder: String? = "",

    @field:SerializedName("accountNo")
    val accountNo: String? = "",
)