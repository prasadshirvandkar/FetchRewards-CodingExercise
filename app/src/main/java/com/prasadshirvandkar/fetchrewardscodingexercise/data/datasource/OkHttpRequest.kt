package com.prasadshirvandkar.fetchrewardscodingexercise.data.datasource

import okhttp3.Request
import okhttp3.Response
import okhttp3.OkHttpClient

class OkHttpRequest(private val client: OkHttpClient) {
    fun getRequest(url: String): Response {
        val request = Request.Builder().url(url).build()
        return client.newCall(request).execute()
    }
}