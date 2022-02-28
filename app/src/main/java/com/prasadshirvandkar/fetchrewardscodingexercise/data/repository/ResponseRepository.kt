package com.prasadshirvandkar.fetchrewardscodingexercise.data.repository

import com.prasadshirvandkar.fetchrewardscodingexercise.data.datasource.OkHttpRequest
import okhttp3.Response

class ResponseRepository(private val okHttpRequest: OkHttpRequest) {

    fun getDataFromAWS(url: String): Response {
        return okHttpRequest.getRequest(url)
    }

}