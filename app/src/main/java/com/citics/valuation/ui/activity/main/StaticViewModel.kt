package com.citics.valuation.ui.activity.main

import androidx.lifecycle.viewModelScope
import citics.sharing.data.model.response.*
import citics.sharing.data.repository.Resource
import citics.sharing.service.customadapter.NetworkResponse
import com.citics.valuation.ui.base.BaseViewModel
import com.citics.valuation.utils.LoaiTaiSan
import com.citics.valuation.utils.StaticDataUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StaticViewModel @Inject constructor() : BaseViewModel() {

    private val _staticNhaDatData: MutableStateFlow<Resource<StaticNhaDatResponse.Data?>> =
        MutableStateFlow(Resource.Loading())
    val staticNhaDatData: StateFlow<Resource<StaticNhaDatResponse.Data?>> get() = _staticNhaDatData

    private val _staticLandPurpose: MutableStateFlow<Resource<List<DetailUsingPurpose>?>> =
        MutableStateFlow(Resource.Loading())
    val staticLandPurpose: StateFlow<Resource<List<DetailUsingPurpose>?>> get() = _staticLandPurpose

    private val _staticCanHoData: MutableStateFlow<Resource<StaticCanHoResponse.Data?>> =
        MutableStateFlow(Resource.Loading())
    val staticCanHo: StateFlow<Resource<StaticCanHoResponse.Data?>> get() = _staticCanHoData

    private val _templateResponse: MutableStateFlow<Resource<TemplateResponse>> =
        MutableStateFlow(Resource.Loading())
    val templateResponse: StateFlow<Resource<TemplateResponse>> get() = _templateResponse

    private val _legalPropertyResponse: MutableStateFlow<Resource<LegalPropertyResponse>> =
        MutableStateFlow(Resource.Loading())
    val legalPropertyResponse: StateFlow<Resource<LegalPropertyResponse>> get() = _legalPropertyResponse


    fun getStaticNhaDat() {
        if (_staticNhaDatData.value is Resource.Loading || _staticNhaDatData.value is Resource.Failure) {
            viewModelScope.launch(Dispatchers.IO) {
                when (val response = generalRepository.getStaticNhaDatInfo()) {
                    is NetworkResponse.Success -> {
                        Timber.d("NetworkResponse.Success")
                        _staticNhaDatData.value = Resource.Success(response.body.data)
                        StaticDataUtils.staticNhaDat = response.body.data
                    }
                    is NetworkResponse.ApiError -> {
                        Timber.d("NetworkResponse.ApiError")
//                        _staticNhaDatData.value =
//                            Resource.Failure(response.body.message, response.body.code)
                    }
                    else -> {
//                        _staticNhaDatData.value =
//                            Resource.Failure("NetworkError", CLIENT_CODE_NETWORK_ERROR)
                    }
                }
            }
        }
    }

    private fun getStaticCanHo() {
        if (_staticCanHoData.value is Resource.Loading || _staticCanHoData.value is Resource.Failure) {
            viewModelScope.launch(Dispatchers.IO) {
                when (val response = generalRepository.getStaticCanHoInfo()) {
                    is NetworkResponse.Success -> {
                        Timber.d("NetworkResponse.Success")
                        StaticDataUtils.staticCanHo = response.body.data
                        _staticCanHoData.value = Resource.Success(response.body.data)
                    }
                    is NetworkResponse.ApiError -> {
                        Timber.d("NetworkResponse.ApiError")
//                        _staticCanHoData.value =
//                            Resource.Failure(response.body.message, response.body.code)
                    }
                    else -> {
//                        _staticCanHoData.value =
//                            Resource.Failure("NetworkError", CLIENT_CODE_NETWORK_ERROR)
                    }
                }
            }
        }
    }

    fun getStaticMucDichSuDungDat() {
        if (_staticLandPurpose.value is Resource.Loading || _staticLandPurpose.value is Resource.Failure) {
            viewModelScope.launch(Dispatchers.IO) {
                when (val response = generalRepository.getLandPurposes()) {
                    is NetworkResponse.Success -> {
                        Timber.d("NetworkResponse.Success")
                        StaticDataUtils.staticMDSDD = response.body.data
                        _staticLandPurpose.value = Resource.Success(response.body.data)
                    }
                    is NetworkResponse.ApiError -> {
                        Timber.d("NetworkResponse.ApiError")
//                        _staticLandPurpose.value =
//                            Resource.Failure(response.body.message, response.body.code)
                    }
                    else -> {
//                        _staticLandPurpose.value =
//                            Resource.Failure("NetworkError", CLIENT_CODE_NETWORK_ERROR)
                    }
                }
            }
        }
    }

    fun getConfigurationFull() {
        if (_templateResponse.value is Resource.Loading || _templateResponse.value is Resource.Failure) {
            viewModelScope.launch(Dispatchers.IO) {
                _templateResponse.value = generalRepository.getTemplate().handleResponse()
                StaticDataUtils.staticFull = _templateResponse.value.data?.data
            }
        }
    }

    fun getStaticLegalProperty(type: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = generalRepository.getStaticLegalProperty(type)) {
                is NetworkResponse.Success -> {
                    Timber.d("NetworkResponse.Success")
                    response.body.data?.let { data ->
                        val result = Resource.Success(data)
                        _legalPropertyResponse.value = result
                        if (type == LoaiTaiSan.CAN_HO.type) {
                            StaticDataUtils.listLegalStaticCanHo =
                                result.data?.legal ?: listOf()
                        } else {
                            StaticDataUtils.listLegalStaticNhaDat =
                                result.data?.legal ?: listOf()
                        }
                    } ?: kotlin.run {
//                        _legalPropertyResponse.value =
//                            Resource.Failure("ApiError", CLIENT_CODE_NETWORK_ERROR)
                    }
                }
                is NetworkResponse.ApiError -> {
                    Timber.d("NetworkResponse.ApiError")
//                    _legalPropertyResponse.value =
//                        Resource.Failure(response.body.message, response.body.code)
                }
                else -> {
//                    _legalPropertyResponse.value =
//                        Resource.Failure("NetworkError", CLIENT_CODE_NETWORK_ERROR)
                }
            }
        }
    }

    fun getAllStaticApp(onDone: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val job1 = async { getStaticCanHo() }
            val job2 = async { getStaticNhaDat() }
            val job3 = async { getConfigurationFull() }
            val job4 = async { getStaticLegalProperty(LoaiTaiSan.CAN_HO.type) }
            val job5 = async { getStaticLegalProperty(LoaiTaiSan.NHA_DAT.type) }
            val job6 = async { getStaticMucDichSuDungDat() }
            job1.await()
            job2.await()
            job3.await()
            job4.await()
            job5.await()
            job6.await()
            withContext(Dispatchers.Main) {
                onDone.invoke()
            }
        }
    }
}