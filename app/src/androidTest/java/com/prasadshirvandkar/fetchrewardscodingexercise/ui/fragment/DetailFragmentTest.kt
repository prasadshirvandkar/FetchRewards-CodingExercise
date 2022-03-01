package com.prasadshirvandkar.fetchrewardscodingexercise.ui.fragment

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.prasadshirvandkar.fetchrewardscodingexercise.Constants.REQUEST_KEY
import com.prasadshirvandkar.fetchrewardscodingexercise.R
import com.prasadshirvandkar.fetchrewardscodingexercise.TestUtils.atPositionOnView
import com.prasadshirvandkar.fetchrewardscodingexercise.TestUtils.matchSize
import com.prasadshirvandkar.fetchrewardscodingexercise.ui.adapter.NameAdapter
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailFragmentTest {

    @Test
    fun testFragmentUISuccess() {
        val stringArrayList = arrayListOf<String>()
        (30 downTo 0).forEach {
            stringArrayList.add("Item $it")
        }
        launchScenario(stringArrayList)
        onView(withId(R.id.recyclerview_names)).check(matches(isDisplayed()))
        for (i in 0..10) {
            onView(withId(R.id.recyclerview_names)).check(matches(
                atPositionOnView(i, withText("Item $i"), R.id.text_name)))
        }
    }

    @Test
    fun testFragmentUISuccessScrolled() {
        val stringArrayList = arrayListOf<String>()
        for (i in 0..60) {
            stringArrayList.add("Item $i")
        }
        launchScenario(stringArrayList)
        onView(withId(R.id.recyclerview_names)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerview_names)).check(matches(matchSize("21")))
        onView(withId(R.id.recyclerview_names)).perform(
            RecyclerViewActions.scrollToPosition<NameAdapter.ViewHolder>(20))
    }

    @Test
    fun testFragmentUIEmptyList() {
        val stringArrayList = arrayListOf<String>()
        launchScenario(stringArrayList)
        onView(withId(R.id.recyclerview_names)).check(matches(not(isDisplayed())))
        onView(withId(R.id.recyclerview_names)).check(matches(matchSize("0")))
    }

    @Test
    fun testFragmentUINullArguments() {
        launchFragmentInContainer<DetailFragment>(
            themeResId = R.style.Theme_FetchRewardsCodingExercise,
        ).moveToState(Lifecycle.State.STARTED)
        onView(withId(R.id.recyclerview_names)).check(matches(not(isDisplayed())))
        onView(withId(R.id.recyclerview_names)).check(matches(matchSize("0")))
    }

    @Test
    fun testSlicedItems() {
        val stringArrayList = arrayListOf<String>()
        (0..60).forEach { stringArrayList.add("Item $it") }

        var newArrayList: List<String?>
        with(DetailFragment.newInstance()) {
            newArrayList = stringArrayList.getSlicedItems(20)
        }

        assertEquals("Item 40", newArrayList[newArrayList.size - 1])
    }

    @Test
    fun testSlicedItemsLessItems() {
        val stringArrayList = arrayListOf<String>()
        (0..15).forEach { stringArrayList.add("Item $it") }

        var newArrayList: List<String?>
        with(DetailFragment.newInstance()) {
            newArrayList = stringArrayList.getSlicedItems(10)
        }

        assertEquals("Item 15", newArrayList[newArrayList.size - 1])
    }

    private fun launchScenario(data: ArrayList<String>): FragmentScenario<DetailFragment> {
        val scenario = launchFragmentInContainer<DetailFragment>(
            themeResId = R.style.Theme_FetchRewardsCodingExercise,
        ).moveToState(Lifecycle.State.STARTED)
        scenario.onFragment{
            it.parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(Pair("names", data), Pair("key", 1)))
        }
        return scenario
    }

}