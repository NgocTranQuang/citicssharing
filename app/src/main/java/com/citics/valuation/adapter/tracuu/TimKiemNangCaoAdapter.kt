package com.citics.valuation.adapter.tracuu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.citics.cagent.data.model.response.CanHoFilterAdvanceResponse
import com.citics.cbank.databinding.LayoutMapFilterAddressItemBinding

/**
 * Created by ChinhQT on 19/10/2022.
 */
class TimKiemNangCaoAdapter(private val onClick: (CanHoFilterAdvanceResponse.ContentItem) -> Unit) :
    ListAdapter<CanHoFilterAdvanceResponse.ContentItem, TimKiemNangCaoAdapter.CanHoTimKiemViewHolder>(
        TimKiemNangCaoItemDiffCallback
    ) {

    class CanHoTimKiemViewHolder(
        private val binding: LayoutMapFilterAddressItemBinding,
        val onClick: (CanHoFilterAdvanceResponse.ContentItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(address: CanHoFilterAdvanceResponse.ContentItem) {
            itemView.setOnClickListener {
                onClick(address)
            }

            binding.contentTxt.text = address.text
            binding.subContentTxt.text = address.sub_text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CanHoTimKiemViewHolder {
        val binding = LayoutMapFilterAddressItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CanHoTimKiemViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: CanHoTimKiemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

object TimKiemNangCaoItemDiffCallback :
    DiffUtil.ItemCallback<CanHoFilterAdvanceResponse.ContentItem>() {
    override fun areItemsTheSame(
        oldItem: CanHoFilterAdvanceResponse.ContentItem,
        newItem: CanHoFilterAdvanceResponse.ContentItem
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: CanHoFilterAdvanceResponse.ContentItem,
        newItem: CanHoFilterAdvanceResponse.ContentItem
    ): Boolean {
        val old = (oldItem.text + oldItem.sub_text).hashCode()
        val new = (newItem.text + newItem.sub_text).hashCode()
        return old == new
    }

}