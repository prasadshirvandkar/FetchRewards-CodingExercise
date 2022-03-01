package com.prasadshirvandkar.fetchrewardscodingexercise.data.model

import org.junit.Assert.*
import org.junit.Test

class UrlResponseTest {
    @Test
    fun testMapConversion() {
        val responseList = mutableListOf<UrlResponse>()
        (0..15).forEachIndexed { i, _ ->
            responseList.add(UrlResponse(1, i % 3, "test${i}"))
        }
        responseList.add(UrlResponse(1, 2, null))
        responseList.add(UrlResponse(1, 2, ""))

        val mappedResponse = responseList.convertToMap()
        assertEquals(3, mappedResponse.size)

        var count = 0
        mappedResponse.forEach { (k, _) -> assertEquals(count++, k) }
    }

}