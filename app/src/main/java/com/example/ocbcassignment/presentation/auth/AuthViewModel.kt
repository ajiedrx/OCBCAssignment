package com.example.ocbcassignment.presentation.auth

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ocbcassignment.base.BaseResult
import com.example.ocbcassignment.data.model.auth.request.AuthRequest
import com.example.ocbcassignment.domain.UseCase
import com.example.ocbcassignment.domain.model.Login
import com.example.ocbcassignment.domain.model.Register
import com.example.ocbcassignment.misc.Const
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AuthViewModel(
    private val interactor: UseCase,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    private var _login: MutableLiveData<BaseResult<Login>> = MutableLiveData<BaseResult<Login>>()
    val login: LiveData<BaseResult<Login>> by lazy { _login }

    private var _register: MutableLiveData<BaseResult<Register>> =
        MutableLiveData<BaseResult<Register>>()
    val register: LiveData<BaseResult<Register>> by lazy { _register }

    fun doLogin(request: AuthRequest) {
        viewModelScope.launch {
            interactor
                .login(request)
                .onStart { _login.value = BaseResult.Loading }
                .catch { _login.value = BaseResult.Error(it.message.toString()) }
                .collect {
                    _login.value = BaseResult.Success(it)
                }
        }
    }

    fun register(request: AuthRequest) {
        viewModelScope.launch {
            interactor
                .register(request)
                .onStart { _register.value = BaseResult.Loading }
                .catch { _register.value = BaseResult.Error(it.message.toString()) }
                .collect {
                    _register.value = BaseResult.Success(it)
                }
        }
    }

    fun saveLoginInformation(loginInfo: Login) {
        sharedPreferences.edit()
            .putString(Const.USER_ACCESS_TOKEN, loginInfo.token)
            .putString(Const.USERNAME, loginInfo.username)
            .putString(Const.USER_ACCOUNT_NO, loginInfo.accountNo)
            .apply()
    }

    fun getLoggedUserInfo(): Login {
        return Login(
            username = sharedPreferences.getString(Const.USERNAME, "").toString(),
            accountNo = sharedPreferences.getString(Const.USER_ACCOUNT_NO, "").toString(),
            token = "", status = ""
        )
    }

    fun clearLoginInformation() {
        sharedPreferences.edit().clear().commit()
    }
}