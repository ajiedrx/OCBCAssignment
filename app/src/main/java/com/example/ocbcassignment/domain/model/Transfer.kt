package com.example.ocbcassignment.domain.model

import com.google.gson.annotations.SerializedName

data class Transfer(

    @field:SerializedName("amount")
    val amount: Int? = null,

    @field:SerializedName("recipientAccount")
    val recipientAccount: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("transactionId")
    val transactionId: String? = null,

    @field:SerializedName("status")
    val status: String? = null,
)
