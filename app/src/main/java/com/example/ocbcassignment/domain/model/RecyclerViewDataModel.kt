package com.example.ocbcassignment.domain.model

sealed class RecyclerViewDataModel {
    data class Header(
        val date: String,
    ) : RecyclerViewDataModel()

    data class Item(
        val item: Transaction,
    ) : RecyclerViewDataModel()
}
