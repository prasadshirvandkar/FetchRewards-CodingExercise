package com.prasadshirvandkar.fetchrewardscodingexercise.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.transition.MaterialSharedAxis
import com.prasadshirvandkar.fetchrewardscodingexercise.R
import com.prasadshirvandkar.fetchrewardscodingexercise.ui.activity.MainActivity
import com.prasadshirvandkar.fetchrewardscodingexercise.ui.adapter.NameAdapter


class DetailFragment: Fragment(), DefaultLifecycleObserver {

    companion object {
        fun newInstance() = DetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.second_fragment, container, false)

        /*enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }*/
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }

        val nestedAdapter = NameAdapter()
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = nestedAdapter
            hasFixedSize()
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        setFragmentResultListener("requestKey") { _, bundle ->
            val names = bundle.getStringArrayList("names")
            nestedAdapter.setNames(names!!)

            /*recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == names.size - 1) {
                        Log.v("TAG", "END")
                    }
                }
            })*/

            (activity as MainActivity).supportActionBar!!.apply {
                title = bundle.getInt("key").toString()
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.arrow_left)
            }
        }

        return view
    }

}