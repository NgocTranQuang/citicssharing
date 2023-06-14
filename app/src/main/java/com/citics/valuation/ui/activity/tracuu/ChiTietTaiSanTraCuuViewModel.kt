package com.citics.valuation.ui.activity.tracuu

import androidx.lifecycle.viewModelScope
import com.citics.cagent.data.model.response.AssetDetailResponse
import com.citics.valuation.data.repository.AssetRepository
import com.citics.valuation.data.repository.Resource
import com.citics.valuation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChiTietTaiSanTraCuuViewModel @Inject constructor(private val assetRepository: AssetRepository) :
    BaseViewModel() {

    private val _assetDetail: MutableStateFlow<Resource<AssetDetailResponse>> =
        MutableStateFlow(Resource.Loading())
    val assetDetail: StateFlow<Resource<AssetDetailResponse>> get() = _assetDetail

    fun getProjectDetail(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _assetDetail.value = assetRepository.getProjectDetail(id).handleResponse()
        }
    }

    fun getCanHoDetail(hasMap: HashMap<String,Any?>) {
        viewModelScope.launch(Dispatchers.IO) {
            _assetDetail.value =
                assetRepository.getApartmentAsset(hasMap).handleResponse()
        }
    }

    fun getLandDetailByLatLng(
        lat: Double, lng: Double
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _assetDetail.value = assetRepository.getLandDetailByLatLng(
                lat, lng
            ).handleResponse()
        }
    }

    fun vote(assetId: String, voted: Boolean?) {
        viewModelScope.launch(Dispatchers.IO) {
//            _voteResponse.value = assetRepository.getAssetVote(assetId, voted).handleResponse()
        }
    }

    fun updateAssetDetail(asset: AssetDetailResponse) {
        _assetDetail.value = Resource.Success(asset)
    }
}