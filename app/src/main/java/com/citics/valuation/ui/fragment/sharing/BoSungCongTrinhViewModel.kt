package com.citics.valuation.ui.fragment.sharing

import androidx.lifecycle.viewModelScope
import citics.sharing.data.model.request.EstimationConstructionRequest
import citics.sharing.data.model.response.EstimationConstructionResponse
import citics.sharing.data.model.response.base.BaseResponse
import citics.sharing.data.repository.Resource
import citics.sharing.data.repository.ValidationRepository
import com.citics.valuation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by ChinhQT on 16/01/2023.
 */
@HiltViewModel
class BoSungCongTrinhViewModel @Inject constructor(private val validationRepository: ValidationRepository) :
    BaseViewModel() {

    private var tinhDGXDJob: Job? = null

    private val _estimation: MutableStateFlow<Resource<BaseResponse<EstimationConstructionResponse, Any>>> =
        MutableStateFlow(Resource.None())
    val estimation: StateFlow<Resource<BaseResponse<EstimationConstructionResponse, Any>>> get() = _estimation

    fun calculateDonGiaXd(rq: EstimationConstructionRequest) {
        tinhDGXDJob?.cancel()
        tinhDGXDJob = viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            _estimation.value = validationRepository.estimationConstruction(rq).handleResponse()
        }
    }
}