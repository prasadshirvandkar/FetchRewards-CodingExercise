package com.prasadshirvandkar.fetchrewardscodingexercise.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beust.klaxon.Klaxon
import com.prasadshirvandkar.fetchrewardscodingexercise.Constants.TAG_RESPONSE
import com.prasadshirvandkar.fetchrewardscodingexercise.data.datasource.OkHttpRequest
import com.prasadshirvandkar.fetchrewardscodingexercise.data.model.UrlResponse
import com.prasadshirvandkar.fetchrewardscodingexercise.data.model.convertToMap
import com.prasadshirvandkar.fetchrewardscodingexercise.data.repository.ResponseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient

class MainViewModel : ViewModel() {

    private var responseRepository: ResponseRepository
    var urlResponse: MutableLiveData<Map<Int, List<String?>>> = MutableLiveData()
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    init {
        val okHttpClient = OkHttpClient()
        val okHttpRequest = OkHttpRequest(okHttpClient)
        responseRepository = ResponseRepository(okHttpRequest)
    }

    fun callUrl(url: String) {
        loading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = responseRepository.getDataFromAWS(url)
            try {
                val responseBody: String? = response.body?.string()
                if (responseBody != null) {
                    val parseResponse: List<UrlResponse> = Klaxon().parseArray(responseBody)!!
                    val responseMap = parseResponse.convertToMap()
                    withContext(Dispatchers.Main) {
                        urlResponse.value = responseMap
                        loading.value = false
                    }
                } else {
                    val message = "Response not available"
                    Log.v(TAG_RESPONSE, message)
                    withContext(Dispatchers.Main) {
                        loading.value = false
                        errorMessage.value = message
                    }
                }
            } catch (e: Exception) {
                val message = "Error occurred while getting data from Url"
                Log.e(TAG_RESPONSE, message)
                withContext(Dispatchers.Main) {
                    loading.value = false
                    errorMessage.value = message
                }
            }
        }
    }
}