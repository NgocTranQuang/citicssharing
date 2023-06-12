package com.citics.valuation.adapter.tracuu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.citics.cagent.data.model.response.CanHoSuggestionResponse
import com.citics.cbank.databinding.LayoutMapFilterAddressItemBinding

/**
 * Created by ChinhQT on 19/10/2022.
 */
class MapFilterCanHoTimKiemAdapter(private val onClick: (CanHoSuggestionResponse.ContentItem) -> Unit) :
    ListAdapter<CanHoSuggestionResponse.ContentItem, MapFilterCanHoTimKiemAdapter.CanHoTimKiemViewHolder>(
        FilterCanHoTimKiemItemDiffCallback
    ) {

    class CanHoTimKiemViewHolder(
        private val binding: LayoutMapFilterAddressItemBinding,
        val onClick: (CanHoSuggestionResponse.ContentItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(address: CanHoSuggestionResponse.ContentItem) {
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

object FilterCanHoTimKiemItemDiffCallback :
    DiffUtil.ItemCallback<CanHoSuggestionResponse.ContentItem>() {
    override fun areItemsTheSame(
        oldItem: CanHoSuggestionResponse.ContentItem, newItem: CanHoSuggestionResponse.ContentItem
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: CanHoSuggestionResponse.ContentItem, newItem: CanHoSuggestionResponse.ContentItem
    ): Boolean {
        val old = (oldItem.text + oldItem.sub_text).hashCode()
        val new = (newItem.text + newItem.sub_text).hashCode()
        return old == new
    }

}