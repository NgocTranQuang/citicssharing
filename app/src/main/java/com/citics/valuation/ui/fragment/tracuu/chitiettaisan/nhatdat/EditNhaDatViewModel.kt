package com.citics.valuation.ui.fragment.tracuu.chitiettaisan.nhatdat

import androidx.lifecycle.viewModelScope
import com.citics.cagent.data.model.response.DetailUsingPurpose
import com.citics.cagent.data.model.response.LandDetailResponse
import com.citics.cagent.data.model.response.tham_dinh.Properties
import com.citics.valuation.data.repository.AssetRepository
import com.citics.valuation.data.repository.Resource
import com.citics.valuation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by ChinhQT on 07/11/2022.
 */
@HiltViewModel
class EditNhaDatViewModel @Inject constructor(private val assetRepository: AssetRepository) :
    BaseViewModel() {

    private val _updateLandDetailResponse: MutableStateFlow<Resource<LandDetailResponse>> =
        MutableStateFlow(Resource.Loading())
    val updateLandDetailResponse: StateFlow<Resource<LandDetailResponse>> get() = _updateLandDetailResponse

    fun estimationAsset(
        asset_id: String,
        loai_tai_san: String,
        properties: Properties,
        using_purpose: List<DetailUsingPurpose>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _updateLandDetailResponse.value = assetRepository.estimationAsset(
                asset_id, loai_tai_san, properties, using_purpose
            ).handleResponse()
        }
    }
}