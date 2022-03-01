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
import com.google.android.material.transition.MaterialSharedAxis
import com.prasadshirvandkar.fetchrewardscodingexercise.Constants
import com.prasadshirvandkar.fetchrewardscodingexercise.Constants.REQUEST_KEY
import com.prasadshirvandkar.fetchrewardscodingexercise.Constants.TAG_RESPONSE
import com.prasadshirvandkar.fetchrewardscodingexercise.R
import com.prasadshirvandkar.fetchrewardscodingexercise.ui.adapter.ListIdAdapter
import com.prasadshirvandkar.fetchrewardscodingexercise.ui.viewmodel.MainViewModel

class MainFragment : Fragment(), DefaultLifecycleObserver {

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment().apply {
                arguments = Bundle().apply {
                    putString("url", Constants.FETCHING_URL)
                }
            }
        }
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)

        val view: View = inflater.inflate(R.layout.main_fragment, container, false)

        val loadingIndicator: CircularProgressIndicator = view.findViewById(R.id.load_data)
        val message: AppCompatTextView = view.findViewById(R.id.text_message)

        val listIdAdapter = ListIdAdapter()

        val recyclerViewIds: RecyclerView = view.findViewById(R.id.recyclerview_ids)
        recyclerViewIds.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = listIdAdapter
            hasFixedSize()
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        listIdAdapter.onItemClick = { names, key ->
            setFragmentResult(REQUEST_KEY, bundleOf(Pair("names", names), Pair("key", key)))
            parentFragmentManager.commit {
                replace(R.id.container, DetailFragment.newInstance())
                addToBackStack(TAG_RESPONSE)
            }
        }

        viewModel.urlResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                listIdAdapter.setResponseData(response)
                recyclerViewIds.visibility = View.VISIBLE
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { loadingStatus ->
            if (loadingStatus) {
                loadingIndicator.visibility = View.VISIBLE
                recyclerViewIds.visibility = View.GONE
                message.visibility = View.GONE
            } else {
                loadingIndicator.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                message.visibility = View.VISIBLE
                message.text = errorMessage
                recyclerViewIds.visibility = View.GONE
            }
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycle?.addObserver(ActivityLifeObserver {
            viewModel = ViewModelProvider(this)[MainViewModel::class.java]
            viewModel.callUrl(requireArguments().getString("url", Constants.FETCHING_URL)!!)
            // Toast.makeText(activity, url, Toast.LENGTH_LONG).show()
        })
    }

}