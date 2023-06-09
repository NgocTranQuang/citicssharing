package com.citics.valuation.adapter.tracuu

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ChiTietTaiSanAdapter(var context: Context, var listView: MutableList<View>) :
    RecyclerView.Adapter<ChiTietTaiSanAdapter.RecyclerViewHolder>() {


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        )
    }

    class RecyclerViewHolder(var itemViewd: View) :
        RecyclerView.ViewHolder(itemViewd) {
        var convertView: View? = null
            get() = itemViewd
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = listView.get(viewType)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        return RecyclerViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: RecyclerViewHolder, position: Int) {


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = listView.size

    fun setList(list: MutableList<View>) {
        this.listView = list
        notifyDataSetChanged()
    }

}
