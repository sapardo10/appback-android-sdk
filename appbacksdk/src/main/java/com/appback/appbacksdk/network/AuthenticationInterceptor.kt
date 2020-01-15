package com.appback.appbacksdk.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthenticationInterceptor(val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()

        if (!token.isBlank()) {
            // Request customization: add request headers
            val requestBuilder: Request.Builder = original.newBuilder()
                .header("Authorization", "Bearer $token") // <-- this is the important line
            val request: Request = requestBuilder.build()
            return chain.proceed(request)
        } else {
            return chain.proceed(original)
        }

    }

}