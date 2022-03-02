package com.prasadshirvandkar.fetchrewardscodingexercise.ui.fragment

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.prasadshirvandkar.fetchrewardscodingexercise.Constants
import com.prasadshirvandkar.fetchrewardscodingexercise.R
import com.prasadshirvandkar.fetchrewardscodingexercise.TestUtils.atPositionOnView
import com.prasadshirvandkar.fetchrewardscodingexercise.WaitUntilVisibleAction
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

    @Test
    fun testFragmentUISuccess() {
        Constants.FETCHING_URL.launchScenario()
        onView(withId(R.id.load_data)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerview_ids)).perform(WaitUntilVisibleAction())
        for (i in 1..4) {
            onView(withId(R.id.recyclerview_ids)).check(matches(
                atPositionOnView(i - 1, withText(i.toString()), R.id.text_header)))
        }
        onView(withId(R.id.load_data)).check(matches(not(isDisplayed())))
    }

    @Test
    fun testFragmentUIError() {
        "https://fetch-hiring.s3.amazonaws.com/sample1.json".launchScenario()
        onView(withId(R.id.load_data)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerview_ids)).check(matches(not(isDisplayed())))
        onView(withId(R.id.text_message)).perform(WaitUntilVisibleAction())
        onView(withId(R.id.text_message)).check(matches(withText("Error occurred while getting data from Url")))
        onView(withId(R.id.load_data)).check(matches(not(isDisplayed())))
    }

    private fun String.launchScenario(): FragmentScenario<MainFragment> {
        return launchFragmentInContainer<MainFragment>(
            themeResId = R.style.Theme_FetchRewardsCodingExercise,
            fragmentArgs = bundleOf("url" to this)
        ).moveToState(Lifecycle.State.STARTED)
    }
}