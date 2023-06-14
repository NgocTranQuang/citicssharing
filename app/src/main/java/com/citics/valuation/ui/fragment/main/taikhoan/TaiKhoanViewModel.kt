package com.citics.valuation.ui.fragment.main.taikhoan

import androidx.lifecycle.viewModelScope
import citics.sharing.data.model.response.UserProfileResponse
import citics.sharing.data.repository.Resource
import citics.sharing.data.repository.UserRepository
import com.citics.valuation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaiKhoanViewModel @Inject constructor(private val validationRepository: UserRepository) :
    BaseViewModel() {

    private val _userprofileResponse: MutableStateFlow<Resource<UserProfileResponse>> =
        MutableStateFlow(Resource.Loading())
    val userprofileResponse: StateFlow<Resource<UserProfileResponse>> get() = _userprofileResponse

    fun getUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            _userprofileResponse.value = validationRepository.getUserProfile().handleResponse()
        }
    }

}