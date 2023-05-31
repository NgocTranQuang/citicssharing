package com.citics.valuation.ui.activity.phuongthucnhancpoint

import androidx.lifecycle.viewModelScope
import com.citics.cagent.data.model.response.base.BaseResponse
import com.citics.cagent.data.model.response.cpoint.DepositMethodResponse
import com.citics.valuation.data.repository.Resource
import com.citics.valuation.data.repository.UserRepository
import com.citics.valuation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by ChinhQT on 28/10/2022.
 */
@HiltViewModel
class PhuongThucNhanCPointViewModel @Inject constructor(val repository: UserRepository) :
    BaseViewModel() {

    private val _methods: MutableStateFlow<Resource<BaseResponse<List<DepositMethodResponse>, Any>>> =
        MutableStateFlow(Resource.Loading())
    val methods: StateFlow<Resource<BaseResponse<List<DepositMethodResponse>, Any>>> get() = _methods


    fun getDepositMethods() {
        viewModelScope.launch(Dispatchers.IO) {
            _methods.value = repository.getDepositMethods().handleResponse()
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.e("onCleared")
    }
}