package com.example.ocbcassignment.data.model.feature.response

import com.google.gson.annotations.SerializedName

data class TransactionsResponse(

    @field:SerializedName("data")
    val data: List<TransactionItem>? = null,

    @field:SerializedName("status")
    val status: String? = null,
)