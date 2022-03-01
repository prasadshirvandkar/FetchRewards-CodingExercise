package com.prasadshirvandkar.fetchrewardscodingexercise.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.prasadshirvandkar.fetchrewardscodingexercise.R


class ListIdAdapter : RecyclerView.Adapter<ListIdAdapter.ViewHolder>() {
    private var responseData = mapOf<Int, List<String?>>()
    private var ids = listOf<Int>()
    private var lastPosition = -1

    var onItemClick: ((List<String?>, Int) -> Unit)? = null

    fun setResponseData(responseData: Map<Int, List<String?>>) {
        this.responseData = responseData
        ids = responseData.keys.toList()
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.listid_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = ids[position].toString()
        setFadeAnimation(viewHolder.itemView, position);
    }

    override fun getItemCount() = responseData.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: AppCompatTextView = view.findViewById(R.id.text_header)

        init {
            view.setOnClickListener {
                onItemClick?.invoke(responseData[ids[adapterPosition]]!!, ids[adapterPosition])
            }
        }
    }

    private fun setFadeAnimation(view: View, position: Int) {
        if(position > lastPosition) {
            view.startAnimation(AlphaAnimation(0.0f, 1.0f).apply { duration = 300 })
            lastPosition = position
        }
    }
}