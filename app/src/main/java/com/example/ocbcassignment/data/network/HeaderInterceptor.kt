package com.example.ocbcassignment.data.network

import android.content.SharedPreferences
import android.util.Log
import com.example.ocbcassignment.misc.Const
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor(
    private val headers: HashMap<String, String>,
    private val sharedPreferences: SharedPreferences,
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("<>HeaderIn", "intercept")
        val request = mapHeaders(chain)
        return chain.proceed(request)
    }

    private fun mapAccessToken() {
        headers["Content-Type"] = "application/json"
        val peekAuthToken = sharedPreferences.getString(Const.USER_ACCESS_TOKEN, "")
        Log.d("<>peekAuthToken", peekAuthToken.toString())
        headers["Authorization"] = "$peekAuthToken"
    }

    private fun mapHeaders(chain: Interceptor.Chain): Request {
        var original = chain.request()
        val headersMap = original.headers.values("Authorization")

        if (headersMap.any()) {
            original = original.newBuilder().removeHeader("Authorization").build()
        }

        mapAccessToken()

        val requestBuilder = original.newBuilder()
        for ((key, value) in headers) {
            Log.d("<>Key", key)
            Log.d("<>Value", value)
            requestBuilder.addHeader(key, value)
        }
        return requestBuilder.build()
    }
}
