package com.example.ocbcassignment.domain

import com.example.ocbcassignment.data.IDataRepository
import com.example.ocbcassignment.data.model.auth.request.AuthRequest
import com.example.ocbcassignment.data.model.feature.request.TransferRequest
import com.example.ocbcassignment.domain.model.*
import kotlinx.coroutines.flow.Flow

class Interactor(private val repository: IDataRepository) : UseCase {
    override suspend fun login(request: AuthRequest): Flow<Login> {
        return repository.login(request)
    }

    override suspend fun register(request: AuthRequest): Flow<Register> {
        return repository.register(request)
    }

    override suspend fun transfer(request: TransferRequest): Flow<Transfer> {
        return repository.transfer(request)
    }

    override suspend fun balance(): Flow<Balance> {
        return repository.balance()
    }

    override suspend fun payees(): Flow<List<Payees>?> {
        return repository.payees()
    }

    override suspend fun transaction(): Flow<List<Transaction>?> {
        return repository.transaction()
    }
}