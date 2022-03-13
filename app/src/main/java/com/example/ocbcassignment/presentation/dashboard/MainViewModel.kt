package com.example.ocbcassignment.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ocbcassignment.base.BaseResult
import com.example.ocbcassignment.data.model.feature.request.TransferRequest
import com.example.ocbcassignment.domain.UseCase
import com.example.ocbcassignment.domain.model.Balance
import com.example.ocbcassignment.domain.model.Payees
import com.example.ocbcassignment.domain.model.Transaction
import com.example.ocbcassignment.domain.model.Transfer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val interactor: UseCase) : ViewModel() {

    private var _transfer: MutableLiveData<BaseResult<Transfer>> =
        MutableLiveData<BaseResult<Transfer>>()
    val transfer: LiveData<BaseResult<Transfer>> by lazy { _transfer }

    private var _balance: MutableLiveData<BaseResult<Balance>> =
        MutableLiveData<BaseResult<Balance>>()
    val balance: LiveData<BaseResult<Balance>> by lazy { _balance }

    private var _payess: MutableLiveData<BaseResult<List<Payees>>> =
        MutableLiveData<BaseResult<List<Payees>>>()
    val payess: LiveData<BaseResult<List<Payees>>> by lazy { _payess }

    private var _transaction: MutableLiveData<BaseResult<List<Transaction>>> =
        MutableLiveData<BaseResult<List<Transaction>>>()
    val transaction: LiveData<BaseResult<List<Transaction>>> by lazy { _transaction }

    fun doTransfer(request: TransferRequest) {
        viewModelScope.launch {
            interactor
                .transfer(request)
                .onStart { _transfer.value = BaseResult.Loading }
                .catch { _transfer.value = BaseResult.Error(it.message.toString()) }
                .collect {
                    _transfer.value = BaseResult.Success(it)
                }
        }
    }

    fun checkBalance() {
        viewModelScope.launch {
            interactor
                .balance()
                .onStart { _balance.value = BaseResult.Loading }
                .catch { _balance.value = BaseResult.Error(it.message.toString()) }
                .collect {
                    _balance.value = BaseResult.Success(it)
                }
        }
    }

    fun checkPayees() {
        viewModelScope.launch {
            interactor
                .payees()
                .onStart { _payess.value = BaseResult.Loading }
                .catch { _payess.value = BaseResult.Error(it.message.toString()) }
                .collect {
                    _payess.value = BaseResult.Success(it!!)
                }
        }
    }

    fun checkTransactions() {
        viewModelScope.launch {
            interactor
                .transaction()
                .onStart { _transaction.value = BaseResult.Loading }
                .catch { _transaction.value = BaseResult.Error(it.message.toString()) }
                .collect {
                    _transaction.value = BaseResult.Success(it!!)
                }
        }
    }
}