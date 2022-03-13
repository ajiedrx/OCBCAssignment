package com.example.ocbcassignment.data

import com.example.ocbcassignment.data.model.auth.request.AuthRequest
import com.example.ocbcassignment.data.model.feature.request.TransferRequest
import com.example.ocbcassignment.domain.model.*
import kotlinx.coroutines.flow.Flow

interface IDataRepository {
    suspend fun login(request: AuthRequest): Flow<Login>

    suspend fun register(request: AuthRequest): Flow<Register>

    suspend fun transfer(request: TransferRequest): Flow<Transfer>

    suspend fun balance(): Flow<Balance>

    suspend fun payees(): Flow<List<Payees>?>

    suspend fun transaction(): Flow<List<Transaction>?>
}