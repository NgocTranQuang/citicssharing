package com.citics.valuation.data.repository

import com.citics.cagent.data.model.request.*
import com.citics.cagent.data.model.response.*
import com.citics.cagent.data.model.response.base.BaseResponse
import com.citics.cagent.data.model.response.cpoint.CPointListResponse
import com.citics.cagent.data.model.response.cpoint.DepositMethodResponse
import com.citics.cagent.data.model.response.hoahong.ContentHoaHongDTO
import com.citics.cagent.data.model.response.hoahong.HoaHongOverviewResponse
import com.citics.cagent.data.model.response.tham_dinh.ResultResponse
import com.citics.cagent.data.repository.customadapter.NetworkResponse
import com.citics.valuation.data.model.response.ErrorResponse
import com.citics.valuation.data.repository.base.BaseRepository
import com.citics.valuation.di.ApiAgent
import com.citics.valuation.di.ApiAgentSearch
import com.citics.valuation.service.APIService
import com.citics.valuation.service.header.ApiHeadersProvider
import com.citics.valuation.utils.RSA
import com.citics.valuation.utils.Utils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ChinhQT on 25/09/2022.
 */
@Singleton
class UserRepository @Inject constructor(
    @ApiAgent private val apiService: APIService,
    @ApiAgentSearch private val apiServiceSearch: APIService
) : BaseRepository() {

//    fun getListHoaHong(
//        filters: HashMap<String, Any?>
//    ): Flow<PagingData<ContentHoaHongDTO>> {
//        return Pager(config = PagingConfig(
//            enablePlaceholders = true, initialLoadSize = PAGE_SIZE_20, pageSize = PAGE_SIZE_20
//        ), pagingSourceFactory = {
//            HoaHongPagingSource(apiService, filters, headers, PAGE_SIZE_20)
//        }).flow
//    }

    suspend fun loginAccount(userRequest: UserRequest): NetworkResponse<UserResponse, ErrorResponse> {
        return apiService.loginAccount(userRequest)
    }

    suspend fun logout(): NetworkResponse<LogoutResponse, ErrorResponse> {
        return apiService.logout()
    }

    fun saveUserDetail(username: String, userResponse: UserResponse) {
        userResponse.data?.let { user ->
            Utils.saveUserAccount(preferenceManager, username, user)
        }
    }

    suspend fun getHoaHongOverview(): NetworkResponse<BaseResponse<HoaHongOverviewResponse, Any>, ErrorResponse> {
        return apiService.getHoaHongOverview()
    }

    suspend fun getHoaHongDetail(id: String?): NetworkResponse<BaseResponse<ContentHoaHongDTO, Any>, ErrorResponse> {
        return apiService.getHoaHongDetail(id)
    }

    suspend fun getCPointCuaBan(): NetworkResponse<BaseResponse<Long?, Any>, ErrorResponse> {
        return apiService.getCPointCuaBan()
    }

    suspend fun getCPointDaDung(): NetworkResponse<BaseResponse<Long?, Any>, ErrorResponse> {
        return apiService.getCPointDaDung()
    }

    suspend fun getDepositMethods(): NetworkResponse<BaseResponse<List<DepositMethodResponse>, Any>, ErrorResponse> {
        return apiService.getDepositMethods()
    }


//    fun getListCPoint(
//        type: Int
//    ): Flow<PagingData<CPointListResponse>> {
//        return Pager(config = PagingConfig(
//            enablePlaceholders = true, initialLoadSize = PAGE_SIZE_20, pageSize = PAGE_SIZE_20
//        ), pagingSourceFactory = {
//            CPointPagingSource(apiService, type, apiHeadersProvider = headers)
//        }).flow
//    }
//
//    fun getListNotification(): Flow<PagingData<ContentNotificationDTO>> {
//        return Pager(config = PagingConfig(
//            enablePlaceholders = true, initialLoadSize = PAGE_SIZE_20, pageSize = PAGE_SIZE_20
//        ), pagingSourceFactory = {
//            NotificationPagingSource(apiService, apiHeadersProvider = headers)
//        }).flow
//    }

    suspend fun forgetPassword(
        forgetPasswordRequest: ForgetPasswordRequest
    ): NetworkResponse<ForgetPasswordResponse, ErrorResponse> {
        return apiService.forgetPassword(forgetPasswordRequest)
    }

    suspend fun confirmOTP(request: OTPRequest): NetworkResponse<OTPResponse, ErrorResponse> {
        return apiService.confirmOTP(request)
    }

    suspend fun confirmOTPLoginInNewDevice(request: OTPRequest): NetworkResponse<OTPResponse, ErrorResponse> {
        return apiService.confirmOTPLoginInNewDevice(request)
    }

    suspend fun resendOTP(request: ResendRequest): NetworkResponse<ResendResponse, ErrorResponse> {
        return apiService.resendOTP(request)
    }

    suspend fun resendOTPInNewDeView(request: ResendRequest): NetworkResponse<ResendResponse, ErrorResponse> {
        return apiService.resendOTPInNewDeView(request)
    }

    suspend fun setNewPassword(request: NewPassRequest): NetworkResponse<NewPassResponse, ErrorResponse> {
        return apiService.setNewPassword(request)
    }

    suspend fun ngungTiepNhanHoSo(ngung: Boolean): NetworkResponse<UserProfileResponse, ErrorResponse> {
        return apiService.ngungTiepNhanHoSo(ngung)
    }

    suspend fun registerUser(
        name: String,
        email: String,
        phone: String,
        tinhThanh: String,
        ngheHienTai: String,
        isDaLamThamDinh: Boolean,
        attachments: List<String>
    ): NetworkResponse<RegisterResponse, ErrorResponse> {
        val body = RegisterRequest(
            name, phone, email, tinhThanh, ngheHienTai, isDaLamThamDinh, attachments
        )
        return apiService.subscribe(body)
    }

    suspend fun getUserProfile(): NetworkResponse<UserProfileResponse, ErrorResponse> {
        return apiService.getUserProfile()
    }

    suspend fun changePassword(changePassRequest: ChangePassRequest): NetworkResponse<ResultResponse, ErrorResponse> {
        return apiService.changePassword(changePassRequest)
    }

    suspend fun registerBiometric(password: String?): NetworkResponse<BaseResponse<RegisterResponseDTO, Any>, ErrorResponse> {
        val rq = password?.let {
            val encryptedAuth = RSA.encrypt("$password", RSA.getBioPublicKey())
            RegisterBiometricRequest(encryptedAuth)
        } ?: kotlin.run {
            RegisterBiometricRequest(null)
        }
        return apiService.registerBiometric(rq)
    }

    suspend fun loginBiometric(rq: BiometricRequest): NetworkResponse<UserResponse, ErrorResponse> {
        return apiService.loginBiometric(rq)
    }

    fun saveFingerId(fingerID: String?) {
        Utils.saveFingerID(preferenceManager, fingerID ?: "")
    }

    fun getFingerID(): String {
        return preferenceManager.fingerID
    }

    fun isShowPopupLoginWithFingerID(): Boolean {
        return preferenceManager.showPopupLoginWithFingerID
    }

    fun isShowPopupLoginWithFingerID(data: Boolean) {
        preferenceManager.showPopupLoginWithFingerID = data
    }

    fun getUsernameInDbLocal(): Pair<String, String> {
        return Pair(preferenceManager.userName, preferenceManager.user_login)
    }

    fun isLogined(): Boolean {
        return preferenceManager.isLogined
    }

    fun clearUsername() {
        preferenceManager.userName = ""
        preferenceManager.user_login = ""
        preferenceManager.isLogined = false
    }

}