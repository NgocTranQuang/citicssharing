package com.citics.valuation.adapter.tracuu

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import citics.sharing.data.model.response.CongTrinh
import com.citics.cbank.databinding.LayoutRowVeCongTrinhBinding
import com.citics.valuation.adapter.base.BaseAdapter
import citics.sharing.extension.setDataColor
import citics.sharing.extension.toShow

class VeCongTrinhAdapter(context: Context, var mapCongTrinh: Map<String, Any>?) :
    BaseAdapter<LayoutRowVeCongTrinhBinding, CongTrinh>(context) {
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> LayoutRowVeCongTrinhBinding
        get() = LayoutRowVeCongTrinhBinding::inflate

    override fun CongTrinh.handleItem(
        binding: LayoutRowVeCongTrinhBinding?, position: Int
    ) {
        setValue(binding, this, position, mapCongTrinh)
    }


    @SuppressLint("SetTextI18n")
    fun setValue(
        binding: LayoutRowVeCongTrinhBinding?,
        data: CongTrinh?,
        position: Int,
        mapCongTrinh: Map<String, Any>?
    ) {
        if (binding == null || data == null) {
            return
        }

        // Set data
        binding.tvTitle.text = data.label
        binding.vLoaiCongTrinh.setValue(data.loaiCongTrinh)
        binding.vDienTich.setValue(data.dien_tich_san.toShow())
        binding.vSoTang.setValue(data.so_tang.toString())
        binding.vNamXayDung.setValue(data.nam_xay_dung.toString())
        binding.vNamSuaChua.setValue(data.nam_sua_chua.toString())

        // Set tag
        (mapCongTrinh?.get("_new") as? ArrayList<Any>)?.getOrNull(position)?.let {
            val map = (it as? Map<String, Any>)
            binding.root.setDataColor(map)
        }
    }
}