package com.citics.valuation.adapter.list_cong_trinh

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import citics.sharing.data.model.response.CongTrinh
import com.citics.cbank.R
import com.citics.cbank.databinding.LayoutRowRoomBinding
import com.citics.valuation.adapter.base.BaseAdapter

class RoomsAdapter(val context: Context, val listData: MutableList<CongTrinh.ExtraRooms>) :
    BaseAdapter<LayoutRowRoomBinding, CongTrinh.ExtraRooms>(context, listData) {
    companion object {
        const val KEY_PHONG_NGU = "Phòng ngủ"
        const val KEY_PHONG_VE_SINH = "Phòng vệ sinh"
        const val KEY_PHONG_KHACH = "Phòng khách"
        const val KEY_PHONG_BEP = "Phòng bếp"
        const val KEY_PHONG_KHAC = "Phòng khác"
    }

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> LayoutRowRoomBinding
        get() = LayoutRowRoomBinding::inflate

    @SuppressLint("SetTextI18n")
    override fun CongTrinh.ExtraRooms.handleItem(binding: LayoutRowRoomBinding?, position: Int) {
        var icon: Int = R.drawable.ic_phong_ngu
        when (title) {
            KEY_PHONG_NGU -> {
                icon = R.drawable.ic_phong_ngu
            }
            KEY_PHONG_VE_SINH -> {
                icon = R.drawable.ic_phong_ngu
            }
            KEY_PHONG_BEP -> {
                icon = R.drawable.ic_phong_ngu
            }
            KEY_PHONG_KHACH -> {
                icon = R.drawable.ic_phong_khach
            }
            else -> {
                icon = R.drawable.ic_star_fill

            }
        }
        binding?.vTf?.bindData(
            title,
            true,
            ContextCompat.getDrawable(context, icon),
            title,
            null,
            null, ""
        )
        binding?.vTf?.setText(so_phong ?: 0) {
            this.so_phong = it
        }
        if (position == (mainList?.size ?: 0) - 1) {
            // the end
            binding?.root?.hideBottomLine()
        } else {
            binding?.root?.showBottomLine()
        }
    }
}