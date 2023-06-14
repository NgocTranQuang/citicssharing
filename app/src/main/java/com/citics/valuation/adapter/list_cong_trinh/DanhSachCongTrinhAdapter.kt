package com.citics.valuation.adapter.list_cong_trinh

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.citics.cagent.data.model.response.CongTrinh
import com.citics.cbank.R
import com.citics.cbank.databinding.LayoutRowDanhSachCongTrinhBinding
import com.citics.valuation.adapter.base.BaseAdapter
import citics.sharing.extension.setTintColor

class DanhSachCongTrinhAdapter(
    var context: Context,
    var lstData: MutableList<CongTrinh>,
    var listValidateFail: MutableList<String>,
    var showValidate: Boolean? = false,
    private var onRemove: ((CongTrinh) -> Unit)? = null,
    var onClick: (String, CongTrinh) -> Unit
) : BaseAdapter<LayoutRowDanhSachCongTrinhBinding, CongTrinh>(context, lstData) {
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> LayoutRowDanhSachCongTrinhBinding
        get() = LayoutRowDanhSachCongTrinhBinding::inflate

    @SuppressLint("SetTextI18n")
    override fun CongTrinh.handleItem(
        binding: LayoutRowDanhSachCongTrinhBinding?, position: Int
    ) {
        binding?.tvName?.text = label
        binding?.dragItem?.setOnClickListener {
            onClick.invoke(binding.tvName.text.toString(), this)
        }
        binding?.root?.isEnabledSwipe = onRemove != null
        binding?.imgDelete?.setOnClickListener {
            onRemove?.invoke(this) ?: kotlin.run {
                remove(this)
            }
        }
        if (showValidate == true) {
            if (listValidateFail.contains(id)) {
                binding?.imgValidate?.setTintColor(R.color.color_orange)
            } else {
                binding?.imgValidate?.setTintColor(R.color.color_blue)
            }
        } else {
            binding?.imgValidate?.visibility = View.GONE
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun remove(item: CongTrinh) {
        mainList?.remove(item)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListValidate(listValidateFail: MutableList<String>) {
        this.listValidateFail = listValidateFail
        notifyDataSetChanged()
    }
}