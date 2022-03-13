package com.example.ocbcassignment.data.model.feature.response

import com.example.ocbcassignment.domain.model.Transfer
import com.google.gson.annotations.SerializedName

data class TransferResponse(

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
) {
    fun mapToDomain(): Transfer {
        return Transfer(
            amount, recipientAccount, description, transactionId, status
        )
    }
}
