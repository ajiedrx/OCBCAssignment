package com.example.ocbcassignment.base

sealed class BaseResult<out R> {
    data class Success<out T>(val data: T) : BaseResult<T>()
    data class Error(val errorMessage: String) : BaseResult<Nothing>()
    object Loading : BaseResult<Nothing>()
    object Empty : BaseResult<Nothing>()
}