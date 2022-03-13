package com.example.ocbcassignment.data.network

import android.content.SharedPreferences
import com.example.ocbcassignment.misc.UnauthorizedAccessEvent
import okhttp3.Interceptor
import okhttp3.Response
import org.greenrobot.eventbus.EventBus
import java.io.IOException

class AuthInterceptor(private val sharedPreferences: SharedPreferences) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(this) {
            val response: Response = chain.proceed(chain.request())
            if (response.code == 401) {
                sharedPreferences.edit().clear().apply()
                EventBus.getDefault().post(UnauthorizedAccessEvent())
            }
            return response
        }
    }
}
