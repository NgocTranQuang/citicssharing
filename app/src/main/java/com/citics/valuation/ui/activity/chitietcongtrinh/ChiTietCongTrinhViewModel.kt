package com.citics.valuation.ui.activity.chitietcongtrinh

import citics.sharing.data.model.response.AssetDetailData
import citics.sharing.data.model.response.CongTrinh
import citics.sharing.data.repository.AssetRepository
import com.citics.valuation.data.model.response.ExtraData
import com.citics.valuation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ChiTietCongTrinhViewModel @Inject constructor(private val userRepository: AssetRepository) :
    BaseViewModel() {

    /* Đây là tài sản chứa công trình đó*/
    private val _asset: MutableStateFlow<AssetDetailData?> =
        MutableStateFlow(null)
    val asset: StateFlow<AssetDetailData?> get() = _asset

    /* Đây là extra data của tài sản chứa công trình đó*/
    private val _extraData: MutableStateFlow<ExtraData?> =
        MutableStateFlow(null)
    val extraData: StateFlow<ExtraData?> get() = _extraData

    /* Đây là công trình mà user đang muốn edit
    * => Nếu nó null => Đang tạo mới công trình
    * => Nếu nó khác null => Đang edit công trình*/
    private val _congTrinh: MutableStateFlow<CongTrinh> =
        MutableStateFlow(CongTrinh())
    val congTrinh: StateFlow<CongTrinh> get() = _congTrinh


    fun setAsset(asset: AssetDetailData) {
        _asset.value = asset
    }

    fun setCongTrinh(ct: CongTrinh) {
        _congTrinh.value = ct
    }

    fun setExtraData(extraData: ExtraData?) {
        _extraData.value = extraData
    }

}