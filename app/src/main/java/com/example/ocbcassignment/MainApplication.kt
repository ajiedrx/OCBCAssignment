package com.example.ocbcassignment

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.ocbcassignment.injection.authModule
import com.example.ocbcassignment.injection.baseModule
import com.example.ocbcassignment.injection.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        startKoin {
            androidContext(this@MainApplication)
            modules(
                listOf(networkModule, authModule, baseModule)
            )

        }
    }
}