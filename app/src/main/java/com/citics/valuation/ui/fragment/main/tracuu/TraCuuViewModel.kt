package com.citics.valuation.ui.fragment.main.tracuu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.citics.valuation.data.repository.AssetRepository
import com.citics.valuation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TraCuuViewModel @Inject constructor(private val validationRepository: AssetRepository) :
    BaseViewModel() {


}