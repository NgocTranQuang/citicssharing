package com.citics.valuation.ui.fragment.login

import androidx.lifecycle.viewModelScope
import com.citics.cagent.data.model.request.BiometricRequest
import com.citics.cagent.data.model.request.NotificationRequest
import com.citics.cagent.data.model.request.UserRequest
import com.citics.cagent.data.model.response.UserResponse
import citics.sharing.service.customadapter.NetworkResponse
import com.citics.valuation.data.repository.NotificationRepository
import com.citics.valuation.data.repository.Resource
import com.citics.valuation.data.repository.UserRepository
import com.citics.valuation.ui.base.BaseViewModel
import com.citics.valuation.utils.RSA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by ChinhQT on 25/09/2022.
 */
@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var notificationRepository: NotificationRepository

    private val _userResponse: MutableStateFlow<Resource<UserResponse>> =
        MutableStateFlow(Resource.Loading())
    val userResponse: StateFlow<Resource<UserResponse>> get() = _userResponse

    private val _userBiometricResponse: MutableStateFlow<Resource<UserResponse>> =
        MutableStateFlow(Resource.Loading())
    val userBiometricResponse: StateFlow<Resource<UserResponse>> get() = _userBiometricResponse


    // Login by username password
    fun loginAccount(username: String, password: String, remember_me: Boolean = false) {

        Timber.d("loginAccount")

        _userResponse.value = Resource.Loading()

        // Create a new coroutine on the UI thread
        viewModelScope.launch(Dispatchers.IO) {
            val encryptedAuth = RSA.encrypt("$username<->$password", RSA.getPublicKey())
            val userRequest = UserRequest(encryptedAuth, remember_me)
            _userResponse.value = userRepository.loginAccount(userRequest).handleResponse {
                userRepository.saveUserDetail(username, it)
            }
        }
    }

    fun loginByBiometric(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val handleIdEncryp = RSA.encrypt(getFingerID(), RSA.getBioPublicKey())

            _userBiometricResponse.value =
                userRepository.loginBiometric(BiometricRequest(signature = handleIdEncryp))
                    .handleResponse {
                        userRepository.saveUserDetail(username, it)

                    }
        }
    }

    fun registerNotification(
        notificationRequest: NotificationRequest,
        onError: () -> Unit,
        onSS: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            when (notificationRepository.registerNotification(notificationRequest)) {
                is NetworkResponse.Success -> {
                    Timber.d("NetworkResponse.Success")
                    onSS.invoke()
                }
                is NetworkResponse.ApiError -> {
                    Timber.d("NetworkResponse.ApiError")
                    onError.invoke()
                }
                is NetworkResponse.NetworkError -> {
                    Timber.d("NetworkResponse.NetworkError")
                    onError.invoke()
                }
                else -> {
                    Timber.d("UnknownError")
                    onError.invoke()
                }
            }
        }
    }

    fun getUsernameInDbLocal(): Pair<String, String> {
        return userRepository.getUsernameInDbLocal()
    }

    fun isLogined(): Boolean {
        return userRepository.isLogined()
    }

    fun getFingerID(): String {
        return userRepository.getFingerID()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.e("onCleared")
    }

    fun isShowPopupLoginWithFingerID(data: Boolean) {
        userRepository.isShowPopupLoginWithFingerID(data)
    }

    fun clearUsername() {
        userRepository.clearUsername()
    }
}