package com.citics.valuation.adapter.cpoint

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import citics.sharing.data.model.response.cpoint.DepositMethodResponse
import com.citics.valuation.adapter.base.BaseLoadMoreAdapter
import com.citics.cbank.databinding.RowItemMethodBinding

class CPointMethodAdapter() :
    BaseLoadMoreAdapter<RowItemMethodBinding, DepositMethodResponse>(BaseDiffUtil()) {

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> RowItemMethodBinding
        get() = RowItemMethodBinding::inflate

    @SuppressLint("SetTextI18n")
    override fun DepositMethodResponse.handleItem(binding: RowItemMethodBinding?, position: Int) {
        binding?.tvCondition?.text = condition
        binding?.tvDepositCP?.text = deposit_cp?.joinToString("\n")
        if (TextUtils.isEmpty(sub_text)) {
            binding?.tvSubText?.visibility = View.GONE
        } else {
            binding?.tvSubText?.visibility = View.VISIBLE
            binding?.tvSubText?.text = "$sub_text"

        }
    }

    class BaseDiffUtil : DiffUtil.ItemCallback<DepositMethodResponse>() {

        override fun areContentsTheSame(
            oldItem: DepositMethodResponse, newItem: DepositMethodResponse
        ): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: DepositMethodResponse, newItem: DepositMethodResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
}