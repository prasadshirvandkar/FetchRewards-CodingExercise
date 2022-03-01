package com.prasadshirvandkar.fetchrewardscodingexercise

import android.util.Log
import android.view.View
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher


object TestUtils {
    fun atPositionOnView(position: Int, itemMatcher: Matcher<View?>, targetViewId: Int): Matcher<View?> {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has view id $itemMatcher at position $position")
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                val targetView: View = viewHolder!!.itemView.findViewById(targetViewId)
                return itemMatcher.matches(targetView)
            }
        }
    }

    fun matchSize(itemMatcher: String): Matcher<View?> {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has view id $itemMatcher at position")
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                Log.v("RECYCLE", recyclerView.adapter?.itemCount.toString())
                return itemMatcher == recyclerView.adapter?.itemCount.toString()
            }
        }
    }
}