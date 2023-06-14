package com.citics.valuation.ui.fragment.main.taikhoan

import androidx.lifecycle.viewModelScope
import citics.sharing.data.model.response.LogoutResponse
import citics.sharing.data.model.response.RegisterResponseDTO
import citics.sharing.data.model.response.UserProfileResponse
import citics.sharing.data.model.response.base.BaseResponse
import citics.sharing.data.model.response.tham_dinh.ResultResponse
import citics.sharing.data.repository.Resource
import citics.sharing.data.repository.UserRepository
import com.citics.valuation.ui.base.BaseViewModel
import com.citics.valuation.utils.DEFAULT
import com.citics.valuation.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by ChinhQT on 02/10/2022.
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {

    private val _userprofileResponse: MutableStateFlow<Resource<UserProfileResponse>> =
        MutableStateFlow(Resource.Loading())
    val userprofileResponse: StateFlow<Resource<UserProfileResponse>> get() = _userprofileResponse

    private val _logoutResponse: MutableStateFlow<Resource<LogoutResponse>> =
        MutableStateFlow(Resource.Loading())
    val logoutResponse: StateFlow<Resource<LogoutResponse>> get() = _logoutResponse

    // To update avatar again after changing avatar
    val loadDataResponse: StateFlow<Resource<ResultResponse>> get() = MutableStateFlow(Resource.Loading())

    private val _registerBiometricResponse: MutableStateFlow<Resource<BaseResponse<RegisterResponseDTO, Any>>> =
        MutableStateFlow(Resource.Loading())
    val registerBiometricResponse: StateFlow<Resource<BaseResponse<RegisterResponseDTO, Any>>> get() = _registerBiometricResponse


    fun registerBiometric(password: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _registerBiometricResponse.value =
                userRepository.registerBiometric(password).handleResponse()
        }
    }

    fun logoutAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            _logoutResponse.value = userRepository.logout().handleResponse {
                Utils.resetUserAccount(preferenceManager)
            }
        }
    }

    fun saveInfo(userProfileResponse: UserProfileResponse? = null) {
        userProfileResponse?.let {
            preferenceManager.userName = it.data?.name ?: DEFAULT
            preferenceManager.email = it.data?.email ?: DEFAULT
            preferenceManager.avatar = it.data?.avatar?.url ?: DEFAULT
            preferenceManager.sodienthoai = it.data?.mobile ?: DEFAULT
            preferenceManager.chucdanh = it.data?.chuc_vu?.title ?: DEFAULT
            preferenceManager.capdochuyenvien = it.data?.cap_do_moi_gioi_title ?: DEFAULT
            preferenceManager.nhomlamviec = it.data?.groups_title?.getOrNull(0) ?: DEFAULT
            preferenceManager.cdxlhs = it.data?.cap_do_xu_ly_ho_so_title ?: DEFAULT

            preferenceManager.ngaysinh = it.data?.ngay_thang_nam_sinh ?: DEFAULT
            preferenceManager.gioitinh = it.data?.gender ?: DEFAULT
            preferenceManager.cmnd = it.data?.card_number ?: DEFAULT
            preferenceManager.diachilienhe = it.data?.address ?: DEFAULT

            preferenceManager.longtitude = it.data?.longitude?.toFloat() ?: 0F
            preferenceManager.latitude = it.data?.latitude?.toFloat() ?: 0F

            preferenceManager.isTiepNhanHoSo = it.data?.off_receive_record ?: false
            preferenceManager.on_receive_record_date = it.data?.on_receive_record_date ?: 0
        }
    }

    fun getUsername(): String = preferenceManager.userName

    fun getAvatar(): String = preferenceManager.avatar

    fun saveFingerId(fingerId: String?) {
        userRepository.saveFingerId(fingerId)
    }

    fun getUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            _userprofileResponse.value = userRepository.getUserProfile().handleResponse()
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("onCleared")
    }

    fun isShowPopupLoginWithFingerID(): Boolean {
        return userRepository.isShowPopupLoginWithFingerID()
    }

    fun isShowPopupLoginWithFingerID(data: Boolean) {
        userRepository.isShowPopupLoginWithFingerID(data)
    }

    fun getFingerId(): String {
        return userRepository.getFingerID()
    }
}