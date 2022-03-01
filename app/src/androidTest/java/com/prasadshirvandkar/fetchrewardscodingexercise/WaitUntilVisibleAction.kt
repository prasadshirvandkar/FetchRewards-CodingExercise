package com.prasadshirvandkar.fetchrewardscodingexercise

import android.view.View
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.util.HumanReadables
import org.hamcrest.CoreMatchers.any
import org.hamcrest.Matcher
import java.util.concurrent.TimeoutException

class WaitUntilVisibleAction(private val timeout: Long = 5000) : ViewAction {
    override fun getConstraints(): Matcher<View> {
        return any(View::class.java)
    }

    override fun getDescription(): String {
        return "wait up to $timeout milliseconds for the view to become visible"
    }

    override fun perform(uiController: UiController, view: View) {

        val endTime = System.currentTimeMillis() + timeout

        do {
            if (view.visibility == View.VISIBLE) return
            uiController.loopMainThreadForAtLeast(50)
        } while (System.currentTimeMillis() < endTime)

        throw PerformException.Builder()
            .withActionDescription(description)
            .withCause(TimeoutException("Waited $timeout milliseconds"))
            .withViewDescription(HumanReadables.describe(view))
            .build()
    }
}