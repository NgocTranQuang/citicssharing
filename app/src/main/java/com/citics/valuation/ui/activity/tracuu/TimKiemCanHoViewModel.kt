package com.citics.valuation.ui.activity.tracuu

import citics.sharing.data.repository.AssetRepository
import com.citics.valuation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Created by ChinhQT on 28/10/2022.
 */
@HiltViewModel
class TimKiemCanHoViewModel @Inject constructor(val assetRepository: AssetRepository) :
    BaseViewModel() {


    private val _mapFilter: MutableStateFlow<HashMap<String, Any?>?> =
        MutableStateFlow(null)
    val mapFilter: StateFlow<HashMap<String, Any?>?> get() = _mapFilter

    private val _projectID: MutableStateFlow<String> =
        MutableStateFlow("")
    val projectID: StateFlow<String> get() = _projectID


    fun setProjectID(projectID: String) {
        _projectID.value = projectID
    }

    fun setMapFilterNangCao(map: HashMap<String, Any?>) {
        _mapFilter.value = map
    }

}