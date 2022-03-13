package com.example.ocbcassignment.data

import com.example.ocbcassignment.data.model.auth.request.AuthRequest
import com.example.ocbcassignment.data.model.auth.response.LoginResponse
import com.example.ocbcassignment.data.model.auth.response.RegisterResponse
import com.example.ocbcassignment.data.model.feature.request.TransferRequest
import com.example.ocbcassignment.data.model.feature.response.BalanceResponse
import com.example.ocbcassignment.data.model.feature.response.PayeesResponse
import com.example.ocbcassignment.data.model.feature.response.TransactionsResponse
import com.example.ocbcassignment.data.model.feature.response.TransferResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: AuthRequest): LoginResponse

    @POST("register")
    suspend fun register(@Body request: AuthRequest): RegisterResponse

    @POST("transfer")
    suspend fun transfer(@Body request: TransferRequest): TransferResponse

    @GET("balance")
    suspend fun balance(): BalanceResponse

    @GET("payees")
    suspend fun payees(): PayeesResponse

    @GET("transactions")
    suspend fun transactions(): TransactionsResponse
}
