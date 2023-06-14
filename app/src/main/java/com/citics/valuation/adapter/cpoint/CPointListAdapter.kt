package com.citics.valuation.adapter.cpoint

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import citics.sharing.data.model.response.cpoint.CPointListResponse
import com.citics.valuation.adapter.base.BaseLoadMoreAdapter
import citics.sharing.extension.setBackgroundTint
import citics.sharing.extension.toShowTime
import com.citics.valuation.utils.CPType
import com.citics.valuation.utils.FORMAT_TIME_THAM_DINH
import com.citics.cbank.R
import com.citics.cbank.databinding.RowCpointItemBinding

class CPointListAdapter(val type: Int) :
    BaseLoadMoreAdapter<RowCpointItemBinding, CPointListResponse>(BaseDiffUtil()) {

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> RowCpointItemBinding
        get() = RowCpointItemBinding::inflate

    @SuppressLint("SetTextI18n")
    override fun CPointListResponse.handleItem(binding: RowCpointItemBinding?, position: Int) {
        val suffix = if (type == CPType.CP_NHAN.type) "+" else "-"
        binding?.tvCountCPoints?.text = "$suffix$cPoint"
        binding?.tvDate?.text = created_date?.toShowTime(FORMAT_TIME_THAM_DINH)
        binding?.tvname?.text = text
        binding?.tvSubText?.text = sub_text
        if (position == 0) {
            binding?.tvDot?.setBackgroundTint(R.color.color_blue)
        } else {
            binding?.tvDot?.setBackgroundTint(R.color.color_divider)
        }
    }

    class BaseDiffUtil : DiffUtil.ItemCallback<CPointListResponse>() {

        override fun areContentsTheSame(
            oldItem: CPointListResponse, newItem: CPointListResponse
        ): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: CPointListResponse, newItem: CPointListResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
}