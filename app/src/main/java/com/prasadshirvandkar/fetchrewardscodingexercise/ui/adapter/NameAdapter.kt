package com.prasadshirvandkar.fetchrewardscodingexercise.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.prasadshirvandkar.fetchrewardscodingexercise.R
import java.util.ArrayList


class NameAdapter : RecyclerView.Adapter<NameAdapter.ViewHolder>() {
    private var names = listOf<String?>()
    private var lastPosition = -1

    fun setNames(names: ArrayList<String?>) {
        this.names = names.sortedBy { it!!.substring(5, it.length).toInt() }
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.name_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = names[position]
        setFadeAnimation(viewHolder.itemView, position);
    }

    override fun getItemCount() = names.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: AppCompatTextView = view.findViewById(R.id.text_name)
    }

    private fun setFadeAnimation(view: View, position: Int) {
        if(position > lastPosition) {
            view.startAnimation(AlphaAnimation(0.0f, 1.0f).apply { duration = 500 })
            lastPosition = position
        }
    }
}