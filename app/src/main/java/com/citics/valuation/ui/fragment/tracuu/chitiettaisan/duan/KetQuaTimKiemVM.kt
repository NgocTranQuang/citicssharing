package com.citics.valuation.ui.fragment.tracuu.chitiettaisan.duan

import androidx.lifecycle.viewModelScope
import com.citics.cagent.data.model.response.CanHoFilterAdvanceResponse
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
class KetQuaTimKiemVM @Inject constructor(private val assetRepository: AssetRepository) :
    BaseViewModel() {

    private val _canHoFilterAdvanceResponse: MutableStateFlow<Resource<CanHoFilterAdvanceResponse>> =
        MutableStateFlow(Resource.Loading())
    val canHoFilterAdvanceResponse: StateFlow<Resource<CanHoFilterAdvanceResponse>> get() = _canHoFilterAdvanceResponse

    fun searchCanHoNangCao(filters: HashMap<String, Any?>) {
        viewModelScope.launch(Dispatchers.IO) {
            _canHoFilterAdvanceResponse.value =
                assetRepository.getCanHoFilterAdvance(filters).handleResponse()
        }
    }
}