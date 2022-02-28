package com.prasadshirvandkar.fetchrewardscodingexercise.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.prasadshirvandkar.fetchrewardscodingexercise.Constants
import com.prasadshirvandkar.fetchrewardscodingexercise.Constants.TAG_RESPONSE
import com.prasadshirvandkar.fetchrewardscodingexercise.R
import com.prasadshirvandkar.fetchrewardscodingexercise.ui.adapter.ListIdAdapter
import com.prasadshirvandkar.fetchrewardscodingexercise.ui.viewmodel.MainViewModel

class MainFragment : Fragment(), DefaultLifecycleObserver {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.main_fragment, container, false)

        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }

        val loadingIndicator: CircularProgressIndicator = view.findViewById(R.id.load_data)
        val message: AppCompatTextView = view.findViewById(R.id.text_message)

        val recAdapter = ListIdAdapter()

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = recAdapter
            hasFixedSize()
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        recAdapter.onItemClick = { names, key ->
            /*exitTransition = MaterialElevationScale(false).apply {
                duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            }*/
            reenterTransition = MaterialElevationScale(true).apply {
                duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            }
            setFragmentResult("requestKey", bundleOf(Pair("names", names), Pair("key", key)))
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                replace(R.id.container, DetailFragment.newInstance())
                addToBackStack(TAG_RESPONSE)
            }
        }

        viewModel.urlResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                recAdapter.setResponseData(response)
                recyclerView.visibility = View.VISIBLE
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { loadingStatus ->
            if (loadingStatus) {
                loadingIndicator.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                message.visibility = View.GONE
            } else {
                loadingIndicator.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                message.visibility = View.VISIBLE
                message.text = errorMessage
                recyclerView.visibility = View.GONE
            }
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycle?.addObserver(ActivityLifeObserver {
            viewModel = ViewModelProvider(this)[MainViewModel::class.java]
            viewModel.callUrl(Constants.FETCHING_URL)
        })
    }

}