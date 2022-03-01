package com.prasadshirvandkar.fetchrewardscodingexercise.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.transition.MaterialSharedAxis
import com.prasadshirvandkar.fetchrewardscodingexercise.Constants.LOG_TAG
import com.prasadshirvandkar.fetchrewardscodingexercise.Constants.REQUEST_KEY
import com.prasadshirvandkar.fetchrewardscodingexercise.R
import com.prasadshirvandkar.fetchrewardscodingexercise.ui.activity.MainActivity
import com.prasadshirvandkar.fetchrewardscodingexercise.ui.adapter.NameAdapter


class DetailFragment: Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)

        val view: View = inflater.inflate(R.layout.second_fragment, container, false)

        var count = 20
        val nameAdapter = NameAdapter()
        val recyclerViewNames: RecyclerView = view.findViewById(R.id.recyclerview_names)
        recyclerViewNames.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = nameAdapter
            hasFixedSize()
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            val names = bundle.getStringArrayList("names")
            Log.v("RECYCLE", names?.size.toString())
            names?.apply {
                sortedBy { it?.getIntValue() }.also {
                    nameAdapter.setNames(it.getSlicedItems(0))

                    recyclerViewNames.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                            if (linearLayoutManager.findLastCompletelyVisibleItemPosition() ==
                                nameAdapter.itemCount - 4 && nameAdapter.itemCount < it.size) {
                                Log.d(LOG_TAG, "Reached End")
                                nameAdapter.addNames(it.getSlicedItems(count))
                                recyclerView.post {
                                    nameAdapter.notifyItemInserted(nameAdapter.itemCount - 21)
                                }
                                count += 20
                            }
                        }
                    })

                    if (activity != null && activity is MainActivity) {
                        (activity as MainActivity).supportActionBar!!.apply {
                            title = bundle.getInt("key").toString()
                            setDisplayHomeAsUpEnabled(true)
                            setHomeAsUpIndicator(R.drawable.arrow_left)
                        }
                    }
                }
            }

        }

        return view
    }

    fun List<String?>.getSlicedItems(start: Int): List<String?> {
        val end =  start + 20
        return this.slice(start..if (end >= this.size) this.size - 1 else end)
    }

    private fun String.getIntValue(): Int {
        return this.substring(5, this.length).toInt()
    }

}