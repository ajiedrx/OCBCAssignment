package com.example.ocbcassignment.data.model.feature.request

import com.google.gson.annotations.SerializedName

data class TransferRequest(

    @field:SerializedName("amount")
    val amount: Int,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("receipientAccountNo")
    val receipientAccountNo: String,
)
