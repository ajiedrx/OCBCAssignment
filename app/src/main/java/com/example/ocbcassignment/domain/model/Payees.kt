package com.example.ocbcassignment.domain.model

import com.google.gson.annotations.SerializedName

data class Payees(

	@field:SerializedName("accountNo")
	val accountNo: String? = "",

	@field:SerializedName("name")
	val name: String? = "",

	@field:SerializedName("id")
	val id: String? = "",
) {
    override fun toString(): String {
        return name.toString()
    }
}