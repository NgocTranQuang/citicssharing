package com.citics.valuation.ui.fragment.tracuu.chitiettaisan.duan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.citics.cagent.data.model.response.AssetDetailResponse
import com.citics.cagent.data.model.response.OptionsSuggestionResponse
import com.citics.cagent.data.model.response.tham_dinh.Properties
import com.citics.valuation.data.repository.AssetRepository
import com.citics.valuation.data.repository.Resource
import com.citics.valuation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditCanHoViewModel @Inject constructor(
    private val assetRepository: AssetRepository, private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {


    private val _optionsSuggestionResponse: MutableStateFlow<Resource<OptionsSuggestionResponse>> =
        MutableStateFlow(Resource.Loading())
    val optionsSuggestionResponse: StateFlow<Resource<OptionsSuggestionResponse>> get() = _optionsSuggestionResponse


    private val _chDetailResponse: MutableStateFlow<Resource<AssetDetailResponse>> =
        MutableStateFlow(Resource.Loading())
    val chDetailResponse: StateFlow<Resource<AssetDetailResponse>> get() = _chDetailResponse


    fun getOptionsSuggestion(
        project_id: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _optionsSuggestionResponse.value = assetRepository.getOptionsSuggestion(
                project_id
            ).handleResponse()
        }
    }

    fun updateCanHo(
        asset_id: String,
        loai_tai_san: String,
        properties: Properties
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _chDetailResponse.value = assetRepository.estimationAsset(
                asset_id,
                loai_tai_san,
                properties,
                null
            ).handleResponse()
        }
    }
}