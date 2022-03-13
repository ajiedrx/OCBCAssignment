package com.example.ocbcassignment

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.ocbcassignment.base.BaseResult
import com.example.ocbcassignment.data.model.auth.request.AuthRequest
import com.example.ocbcassignment.domain.Interactor
import com.example.ocbcassignment.domain.model.Login
import com.example.ocbcassignment.domain.model.Register
import com.example.ocbcassignment.presentation.auth.AuthViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuthViewModelTest {
    private lateinit var authViewModel: AuthViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Mock
    private lateinit var interactor: Interactor

    @Mock
    private lateinit var loginObserver: Observer<BaseResult<Login>>

    @Mock
    private lateinit var registerObserver: Observer<BaseResult<Register>>

    @Mock
    private lateinit var sharedPreferences: SharedPreferences

    @Test
    fun doLogin() {
        authViewModel = AuthViewModel(interactor, sharedPreferences)
        val dummyLoginRequest = AuthRequest("asdasd", "test")
        val loginRequest = MutableLiveData<AuthRequest>()
        loginRequest.value = dummyLoginRequest

        suspend {
            Mockito.`when`(interactor.login(dummyLoginRequest))
                .thenReturn(flow {
                    emit(Login(username = dummyLoginRequest.username,
                        accountNo = "432",
                        status = "0",
                        token = "abcd"))
                })
            authViewModel.doLogin(dummyLoginRequest)
            var loginResult = Login(token = "", status = "", accountNo = "", username = "")
            authViewModel.login.observeForever {
                when (it) {
                    is BaseResult.Success -> loginResult = it.data
                }
            }
            Mockito.verify(interactor).login(dummyLoginRequest)
            Assert.assertNotNull(authViewModel.login)

            assertEquals(dummyLoginRequest.username, loginResult.username)

            authViewModel.login.observeForever(loginObserver)
            Mockito.verify(loginObserver).onChanged(authViewModel.login.value)
        }
    }

    @Test
    fun doRegister() {
        authViewModel = AuthViewModel(interactor, sharedPreferences)
        val dummyRegisterRequest = AuthRequest("asdasd", "test")
        val registerRequest = MutableLiveData<AuthRequest>()
        registerRequest.value = dummyRegisterRequest

        suspend {
            Mockito.`when`(interactor.register(dummyRegisterRequest))
                .thenReturn(flow {
                    emit(Register(status = "0", token = "abcd"))
                })
            authViewModel.doLogin(dummyRegisterRequest)
            var loginResult = Login(token = "", status = "", accountNo = "", username = "")
            authViewModel.login.observeForever {
                when (it) {
                    is BaseResult.Success -> loginResult = it.data
                }
            }
            Mockito.verify(interactor).login(dummyRegisterRequest)
            Assert.assertNotNull(authViewModel.login)

            assertEquals(dummyRegisterRequest.username, loginResult.username)

            authViewModel.register.observeForever(registerObserver)
            Mockito.verify(registerObserver).onChanged(authViewModel.register.value)
        }
    }
}