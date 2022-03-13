package com.example.ocbcassignment.data.model.feature.response

import com.example.ocbcassignment.domain.model.Balance
import com.google.gson.annotations.SerializedName

data class BalanceResponse(

    @field:SerializedName("balance")
    val balance: Double? = null,

    @field:SerializedName("accountNo")
    val accountNo: String? = null,

    @field:SerializedName("status")
    val status: String? = null,
) {
    fun mapToDomain(): Balance {
        return Balance(
            balance, accountNo, status
        )
    }
}
