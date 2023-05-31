package com.citics.valuation.data.repository

import com.citics.cagent.data.model.response.*
import com.citics.cagent.data.model.response.base.BaseResponse
import com.citics.cagent.data.repository.customadapter.NetworkResponse
import com.citics.valuation.data.model.response.ErrorResponse
import com.citics.valuation.data.repository.base.BaseRepository
import com.citics.valuation.di.ApiAgent
import com.citics.valuation.di.ApiAgentSearch
import com.citics.valuation.di.ApiUploader
import com.citics.valuation.service.APIService
import com.citics.valuation.service.header.ApiHeadersProvider
import com.citics.valuation.utils.LoaiTaiSan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ChinhQT on 12/10/2022.
 */
@Singleton
class GeneralRepository @Inject constructor(
    @ApiAgentSearch private val apiServiceSearch: APIService,
    @ApiUploader private val apiServiceForUploader: APIService,
    @ApiAgent private val apiServiceAgent: APIService,
) : BaseRepository() {

    suspend fun getTemplate(): NetworkResponse<TemplateResponse, ErrorResponse> {
        return apiServiceSearch.getTemplate()
    }

    suspend fun avatarUpload(file: MultipartBody.Part): NetworkResponse<BaseResponse<UserProfileResponse.Avatar, Any>, ErrorResponse> {
        return apiServiceForUploader.avatarUpload(file)
    }

    suspend fun agencyPhotoUpload(file: MultipartBody.Part): NetworkResponse<BaseResponse<MutableList<Document>, Any>, ErrorResponse> {
        return apiServiceForUploader.agencyPhotoUpload(file)
    }

    suspend fun getDataAvailable(): NetworkResponse<DataAvailableResponse, ErrorResponse> {
        return apiServiceSearch.getDataAvailable()
    }

    suspend fun getAreas(area: String = ""): NetworkResponse<AreaResponse, ErrorResponse> {
        return apiServiceSearch.getAreas(area)
    }

    suspend fun getAreasSuggestion(
        term: String, limit: Int = 10
    ): NetworkResponse<AreasSuggestionResponse, ErrorResponse> {
        return apiServiceSearch.getAreasSuggestion(term, limit)
    }

    suspend fun getStaticNhaDatInfo(
    ): NetworkResponse<StaticNhaDatResponse, ErrorResponse> {
        return apiServiceSearch.getStaticNhaDatInfo(LoaiTaiSan.NHA_DAT.type.toString())
    }

    suspend fun getStaticCanHoInfo(
    ): NetworkResponse<StaticCanHoResponse, ErrorResponse> {
        return apiServiceSearch.getStaticCanHoInfo(LoaiTaiSan.CAN_HO.type.toString())
    }

    suspend fun getLandPurposes(): NetworkResponse<BaseResponse<List<DetailUsingPurpose>, Any>, ErrorResponse> {
        return apiServiceSearch.getLandPurposes()
    }

    suspend fun legalPhotoUpload(file: MultipartBody.Part): NetworkResponse<BaseResponse<MutableList<Document>, Any>, ErrorResponse> {
        return apiServiceForUploader.legalPhotoUpload(file)
    }

    suspend fun assetPhotoUpload(file: MultipartBody.Part): NetworkResponse<BaseResponse<MutableList<Document>, Any>, ErrorResponse> {
        return apiServiceForUploader.assetPhotoUpload(file)
    }

    suspend fun hoSoFileUpload(file: MultipartBody.Part): NetworkResponse<BaseResponse<List<Document>, Any>, ErrorResponse> {
        return apiServiceForUploader.notePhotoUpload(file)
    }

    suspend fun getStaticLegalProperty(type: Int): NetworkResponse<BaseResponse<LegalPropertyResponse, Any>, ErrorResponse> {
        return apiServiceSearch.legalProperty(type)
    }
}