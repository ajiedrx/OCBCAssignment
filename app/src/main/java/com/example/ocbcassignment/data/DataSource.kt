package com.example.ocbcassignment.data

import com.example.ocbcassignment.data.model.auth.request.AuthRequest
import com.example.ocbcassignment.data.model.feature.request.TransferRequest
import com.example.ocbcassignment.domain.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DataSource(private val apiService: ApiService) : IDataRepository {
    override suspend fun login(request: AuthRequest): Flow<Login> {
        return flow {
            emit(apiService.login(request).mapToDomain())
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun register(request: AuthRequest): Flow<Register> {
        return flow {
            emit(apiService.register(request).mapToDomain())
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun transfer(request: TransferRequest): Flow<Transfer> {
        return flow {
            emit(apiService.transfer(request).mapToDomain())
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun balance(): Flow<Balance> {
        return flow {
            emit(apiService.balance().mapToDomain())
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun payees(): Flow<List<Payees>?> {
        return flow {
            emit(apiService.payees().data?.map { it.mapToDomain() })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun transaction(): Flow<List<Transaction>?> {
        return flow {
            emit(apiService.transactions().data?.map { it.mapToDomain() })
        }.flowOn(Dispatchers.IO)
    }
}