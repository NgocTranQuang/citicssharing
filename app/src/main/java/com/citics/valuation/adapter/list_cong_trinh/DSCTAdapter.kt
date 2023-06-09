package com.citics.valuation.adapter.list_cong_trinh

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.citics.cagent.data.model.response.CongTrinh
import com.citics.cbank.R
import com.citics.cbank.databinding.LayoutRowDanhSachCongTrinhBinding


class DSCTAdapter : ListAdapter<CongTrinh, DSCTAdapter.DSCTViewHolder>(
    DSCHItemDiffCallback
) {

    class DSCTViewHolder(
        private val binding: LayoutRowDanhSachCongTrinhBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(content: CongTrinh, position: Int) {
            binding.tvName.text = itemView.context.getString(R.string.ctxd, position + 1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DSCTViewHolder {
        val binding = LayoutRowDanhSachCongTrinhBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DSCTViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DSCTViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }
}

object DSCHItemDiffCallback : DiffUtil.ItemCallback<CongTrinh>() {
    override fun areItemsTheSame(
        oldItem: CongTrinh, newItem: CongTrinh
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: CongTrinh, newItem: CongTrinh
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

}