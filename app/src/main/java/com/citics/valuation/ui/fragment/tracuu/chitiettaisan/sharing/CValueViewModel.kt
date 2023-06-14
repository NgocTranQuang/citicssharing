package com.citics.valuation.ui.fragment.tracuu.chitiettaisan.sharing

import androidx.lifecycle.viewModelScope
import com.citics.cagent.data.model.response.AssetDetailResponse
import com.citics.valuation.data.repository.AssetRepository
import com.citics.valuation.data.repository.Resource
import com.citics.valuation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CValueViewModel @Inject constructor(private val assetRepository: AssetRepository) :
    BaseViewModel() {

    private val _assetInfoWithCValue =
        MutableStateFlow<Resource<AssetDetailResponse>>(Resource.Loading())
    val assetInfoWithCValue get() = _assetInfoWithCValue


    fun loadCValueOfAsset(myAssetID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _assetInfoWithCValue.value =
                assetRepository.getCValueOfAssetById(myAssetID).handleResponse()
        }
    }
}