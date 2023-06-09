package com.citics.valuation.ui.activity.chitietcongtrinh

import com.citics.cagent.data.model.response.AssetDetailData
import com.citics.cagent.data.model.response.CongTrinh
import com.citics.cagent.data.model.response.base.BaseResponse
import com.citics.cagent.data.model.response.cpoint.DepositMethodResponse
import com.citics.valuation.data.model.response.ExtraData
import com.citics.valuation.data.repository.AssetRepository
import com.citics.valuation.data.repository.Resource
import com.citics.valuation.data.repository.UserRepository
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