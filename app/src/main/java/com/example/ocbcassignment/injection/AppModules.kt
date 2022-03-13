package com.example.ocbcassignment.injection

import android.content.Context
import android.content.SharedPreferences
import com.example.ocbcassignment.data.ApiService
import com.example.ocbcassignment.data.DataSource
import com.example.ocbcassignment.data.IDataRepository
import com.example.ocbcassignment.data.network.AuthInterceptor
import com.example.ocbcassignment.data.network.HeaderInterceptor
import com.example.ocbcassignment.domain.Interactor
import com.example.ocbcassignment.domain.UseCase
import com.example.ocbcassignment.misc.Const
import com.example.ocbcassignment.presentation.auth.AuthViewModel
import com.example.ocbcassignment.presentation.dashboard.MainViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val baseModule = module {
    single<IDataRepository> { DataSource(get()) }
    single<UseCase> { Interactor(get()) }
    viewModel { MainViewModel(get()) }
}

val authModule = module {
    viewModel { AuthViewModel(get(), get()) }
}

private const val baseUrl = "https://green-thumb-64168.uc.r.appspot.com/"
val networkModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences(Const.PREFERENCE_NAME,
            Context.MODE_PRIVATE)
    }
    single {
        val client = OkHttpClient().newBuilder()
            .addInterceptor(getHeaderInterceptor(get()))
            .addInterceptor(AuthInterceptor(get())).build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(
            ApiService::class.java
        )
    }
}

private fun getHeaderInterceptor(sharedPreferences: SharedPreferences): Interceptor {
    val headers = HashMap<String, String>()
    return HeaderInterceptor(headers, sharedPreferences)
}

