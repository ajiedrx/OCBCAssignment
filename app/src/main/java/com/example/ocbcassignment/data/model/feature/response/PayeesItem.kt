package com.example.ocbcassignment.data.model.feature.response

import com.example.ocbcassignment.domain.model.Payees
import com.google.gson.annotations.SerializedName

data class PayeesItem(

    @field:SerializedName("accountNo")
    val accountNo: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: String? = null,
) {
    fun mapToDomain(): Payees {
        return Payees(
            accountNo, name, id
        )
    }
}