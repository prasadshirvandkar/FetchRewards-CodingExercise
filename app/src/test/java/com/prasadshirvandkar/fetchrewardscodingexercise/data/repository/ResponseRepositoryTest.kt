package com.prasadshirvandkar.fetchrewardscodingexercise.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.prasadshirvandkar.fetchrewardscodingexercise.Constants
import com.prasadshirvandkar.fetchrewardscodingexercise.data.datasource.OkHttpRequest
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.net.UnknownHostException

class ResponseRepositoryTest {
    lateinit var responseRepository: ResponseRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        responseRepository = ResponseRepository(OkHttpRequest(OkHttpClient()))
    }

    @Test
    fun getDataFromAWS() {
        val response = responseRepository.getDataFromAWS(Constants.FETCHING_URL)
        assertNotNull(response)
    }

    @Test
    fun getDataFromAWS1() {
        val response = responseRepository.getDataFromAWS("https://fetch-hiring.s3.amazonaws.com/sample1.json")
        assertEquals("<Error>", response.body?.string()?.substring(0, 6))
    }

    @Test(expected = UnknownHostException::class)
    fun getDataFromAWS2() {
        val response = responseRepository.getDataFromAWS("https://fetchhiring.s2.amazonaws.com/sample1.json")
        assertEquals(null, response.body)
    }
}