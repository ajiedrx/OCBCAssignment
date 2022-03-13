package com.example.ocbcassignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.ocbcassignment.base.BaseResult
import com.example.ocbcassignment.data.model.feature.request.TransferRequest
import com.example.ocbcassignment.domain.Interactor
import com.example.ocbcassignment.domain.model.*
import com.example.ocbcassignment.presentation.dashboard.MainViewModel
import junit.framework.Assert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainViewModelTest {
    private lateinit var mainViewModel: MainViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Mock
    private lateinit var interactor: Interactor

    @Mock
    private lateinit var balanceObserver: Observer<BaseResult<Balance>>

    @Mock
    private lateinit var transferObserver: Observer<BaseResult<Transfer>>

    @Mock
    private lateinit var payeesObserver: Observer<BaseResult<List<Payees>>>

    @Mock
    private lateinit var transactionsObserver: Observer<BaseResult<List<Transaction>>>

    @Test
    fun doCheckBalance() {
        mainViewModel = MainViewModel(interactor)

        suspend {
            Mockito.`when`(interactor.balance())
                .thenReturn(flow {
                    emit(Balance(accountNo = "12312-4241", status = "0", balance = 23.4))
                })
            mainViewModel.checkBalance()
            var result = Balance(accountNo = "12312-4241", status = "0", balance = 23.4)
            mainViewModel.balance.observeForever {
                when (it) {
                    is BaseResult.Success -> result = it.data
                }
            }
            Mockito.verify(interactor).balance()
            org.junit.Assert.assertNotNull(result)

            Assert.assertEquals(23.4, result.balance)

            mainViewModel.balance.observeForever(balanceObserver)
            Mockito.verify(balanceObserver).onChanged(mainViewModel.balance.value)
        }
    }

    @Test
    fun doTransfer() {
        mainViewModel = MainViewModel(interactor)

        val dummyTransferRequest = TransferRequest(5, "Desc", "2131-5345-545")

        suspend {
            Mockito.`when`(interactor.transfer(dummyTransferRequest))
                .thenReturn(flow {
                    emit(Transfer(status = "1",
                        amount = 5,
                        description = "Desc",
                        recipientAccount = "2131-5345-545",
                        transactionId = "342fdvc3"))
                })
            mainViewModel.doTransfer(dummyTransferRequest)
            var result = Transfer()
            mainViewModel.transfer.observeForever {
                when (it) {
                    is BaseResult.Success -> result = it.data
                }
            }
            Mockito.verify(interactor).transfer(dummyTransferRequest)
            org.junit.Assert.assertNotNull(result)

            assertEquals(dummyTransferRequest.amount, result.amount)
            assertEquals(dummyTransferRequest.description, result.description)
            assertEquals(dummyTransferRequest.receipientAccountNo, result.recipientAccount)

            mainViewModel.transfer.observeForever(transferObserver)
            Mockito.verify(transferObserver).onChanged(mainViewModel.transfer.value)
        }
    }

    @Test
    fun payees() {
        mainViewModel = MainViewModel(interactor)

        suspend {
            Mockito.`when`(interactor.payees())
                .thenReturn(flow {
                    emit(listOf(
                        Payees(accountNo = "234-456-678", name = "Abc", id = "123"),
                        Payees(accountNo = "235-457-679", name = "Abd", id = "124"),
                        Payees(accountNo = "236-457-680", name = "Abe", id = "125"),
                    ))
                })
            mainViewModel.checkPayees()
            var result = listOf<Payees>()
            mainViewModel.payess.observeForever {
                when (it) {
                    is BaseResult.Success -> result = it.data
                }
            }
            Mockito.verify(interactor).payees()
            org.junit.Assert.assertNotNull(result)

            mainViewModel.payess.observeForever(payeesObserver)
            Mockito.verify(payeesObserver).onChanged(mainViewModel.payess.value)
        }
    }

    @Test
    fun checkTransactions() {
        mainViewModel = MainViewModel(interactor)

        suspend {
            Mockito.`when`(interactor.transaction())
                .thenReturn(flow {
                    emit(listOf(
                        Transaction(
                            transactionId = "234-456-678",
                            description = "desc",
                            amount = 3.0,
                            receipient = Receipient(),
                            transactionDate = "2020-01-10T10:20:30.323Z",
                            transactionType = "transfer")
                    ))
                })
            mainViewModel.checkTransactions()
            var result = listOf<Transaction>()
            mainViewModel.transaction.observeForever {
                when (it) {
                    is BaseResult.Success -> result = it.data
                }
            }
            Mockito.verify(interactor).transaction()
            org.junit.Assert.assertNotNull(result)

            mainViewModel.transaction.observeForever(transactionsObserver)
            Mockito.verify(transactionsObserver).onChanged(mainViewModel.transaction.value)
        }
    }
}