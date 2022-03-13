package com.example.ocbcassignment.domain.model

import com.google.gson.annotations.SerializedName

data class Transaction(

    @field:SerializedName("transactionType")
    val transactionType: String? = "",

    @field:SerializedName("amount")
    val amount: Double? = 0.0,

    @field:SerializedName("receipient")
    val receipient: Receipient? = Receipient(),

    @field:SerializedName("description")
    val description: String? = "",

    @field:SerializedName("transactionDate")
    val transactionDate: String? = "",

    @field:SerializedName("transactionId")
    val transactionId: String? = "",
)