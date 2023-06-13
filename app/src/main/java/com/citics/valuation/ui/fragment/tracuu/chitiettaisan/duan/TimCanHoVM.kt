package com.citics.valuation.ui.fragment.tracuu.chitiettaisan.duan

import androidx.lifecycle.viewModelScope
import com.citics.cagent.data.model.response.CanHoSuggestionResponse
import com.citics.cagent.data.model.response.OptionsSuggestionResponse
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

/**
 * Created by ChinhQT on 10/11/2022.
 */
@HiltViewModel
class TimCanHoVM @Inject constructor(private val assetRepository: AssetRepository) :
    BaseViewModel() {


    private val _optionsSuggestionResponse: MutableStateFlow<Resource<OptionsSuggestionResponse>> =
        MutableStateFlow(Resource.Loading())
    val optionsSuggestionResponse: StateFlow<Resource<OptionsSuggestionResponse>> get() = _optionsSuggestionResponse


    private val _canHoSuggestionResponse: MutableStateFlow<Resource<CanHoSuggestionResponse>> =
        MutableStateFlow(Resource.Loading())
    val canHoSuggestionResponse: StateFlow<Resource<CanHoSuggestionResponse>> get() = _canHoSuggestionResponse

    fun suggestionSearch(
        project_id: String, term: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _canHoSuggestionResponse.value =
                assetRepository.getCanHoSuggestion(project_id, term).handleResponse()
        }
    }


    fun getOptionsSuggestion(
        project_id: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _optionsSuggestionResponse.value = assetRepository.getOptionsSuggestion(
                project_id
            ).handleResponse()
        }
    }
}