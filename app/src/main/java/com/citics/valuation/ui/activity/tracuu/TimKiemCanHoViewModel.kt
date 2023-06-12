package com.citics.valuation.ui.activity.tracuu

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
class TimKiemCanHoViewModel @Inject constructor(val repository: UserRepository) :
    BaseViewModel() {

    private val _projectID: MutableStateFlow<String> =
        MutableStateFlow("")
    val projectID: StateFlow<String> get() = _projectID


    fun setProjectID(projectID: String) {
        _projectID.value = projectID
    }
}