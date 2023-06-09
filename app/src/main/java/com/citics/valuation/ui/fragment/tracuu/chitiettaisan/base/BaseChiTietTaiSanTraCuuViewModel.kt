package com.citics.valuation.ui.fragment.tracuu.chitiettaisan.base

import androidx.lifecycle.viewModelScope
import com.citics.cagent.data.model.response.AssetVoteResponse
import com.citics.cagent.data.model.response.LandDetailResponse
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
class BaseChiTietTaiSanTraCuuViewModel @Inject constructor(private val assetRepository: AssetRepository) :
    BaseViewModel() {

//    private val _assetDetail: MutableStateFlow<LandDetailResponse?> =
//        MutableStateFlow(null)
//    val assetDetail: StateFlow<LandDetailResponse?> get() = _assetDetail
//

    private val _voteResponse: MutableStateFlow<Resource<AssetVoteResponse>> =
        MutableStateFlow(Resource.Loading())
    val voteResponse: StateFlow<Resource<AssetVoteResponse>> = _voteResponse

    fun vote(assetId: String, voted: Boolean?) {
        viewModelScope.launch(Dispatchers.IO) {
            _voteResponse.value = assetRepository.getAssetVote(assetId, voted).handleResponse()
        }
    }

//    fun setAssetDetail(land: LandDetailResponse?) {
//        _assetDetail.value = land
//    }

}