package com.prasadshirvandkar.fetchrewardscodingexercise

import com.prasadshirvandkar.fetchrewardscodingexercise.data.model.UrlResponse
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testMap() {
        val responseList = mutableListOf<UrlResponse>()
        (0..12).forEachIndexed { i, _ ->
            responseList.add(UrlResponse(1, i % 2, "test${i}"))
        }

        val testMap = responseList.filter { !it.name.isNullOrEmpty() }.groupBy { it.listId }
            .mapValues { entry -> entry.value.map { it.name } }
        assertEquals(2, testMap.size)
    }

    @Test
    fun testAdd() {
        assertEquals("114", "Item 114".subSequence(5, 8))
    }
}