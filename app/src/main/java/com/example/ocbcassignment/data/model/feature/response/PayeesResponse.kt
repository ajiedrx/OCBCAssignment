package com.example.ocbcassignment.data.model.feature.response

import com.google.gson.annotations.SerializedName

data class PayeesResponse(

    @field:SerializedName("data")
    val data: List<PayeesItem>? = null,

    @field:SerializedName("status")
    val status: String? = null,
)