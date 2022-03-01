package com.prasadshirvandkar.fetchrewardscodingexercise.ui.viewmodel

import android.os.Handler
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.prasadshirvandkar.fetchrewardscodingexercise.Constants
import com.prasadshirvandkar.fetchrewardscodingexercise.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.junit.Assert.*
import java.util.concurrent.TimeoutException

@ExperimentalCoroutinesApi
class MainViewModelTest {

    lateinit var mainViewModel: MainViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private var handler = Mockito.mock(Handler::class.java)
    private var log = Mockito.mock(Log::class.java)

    private val dispatcher by lazy { TestCoroutineDispatcher() }

    @Before
    fun setUp() {
        mainViewModel = MainViewModel()
        Dispatchers.setMain(dispatcher)
        // mainViewModel.urlResponse.observeForever()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun callUrl() = runTest {
        mainViewModel.callUrl(Constants.FETCHING_URL)
        assertTrue(mainViewModel.loading.getOrAwaitValue())
        assertEquals(4, mainViewModel.urlResponse.getOrAwaitValue().size)
        assertFalse(mainViewModel.loading.getOrAwaitValue())
    }

    @Test
    fun callUnauthorizedUrl() = runTest {
        mainViewModel.callUrl("https://fetch-hiring.s3.amazonaws.com/sample1.json")
        assertTrue(mainViewModel.loading.getOrAwaitValue())
        assertEquals("Error occurred while getting data from Url",
            mainViewModel.errorMessage.getOrAwaitValue())
        assertFalse(mainViewModel.loading.getOrAwaitValue())
    }

    @Test(expected = TimeoutException::class)
    fun callInvalidUrl() = runTest {
        mainViewModel.callUrl("https://fetchhiring.s2.amazonaws.com/sample1.json")
        assertTrue(mainViewModel.loading.getOrAwaitValue())
        assertNull(mainViewModel.errorMessage.getOrAwaitValue().toString())
        assertFalse(mainViewModel.loading.getOrAwaitValue())
    }
}