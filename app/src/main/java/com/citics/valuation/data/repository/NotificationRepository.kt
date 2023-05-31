package com.citics.valuation.data.repository

import com.citics.cagent.data.model.request.NotificationRequest
import com.citics.cagent.data.model.response.NotificationResponse
import com.citics.cagent.data.repository.customadapter.NetworkResponse
import com.citics.valuation.data.model.response.ErrorResponse
import com.citics.valuation.data.repository.base.BaseRepository
import com.citics.valuation.di.ApiAgent
import com.citics.valuation.service.APIService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ChinhQT on 31/10/2022.
 */
@Singleton
class NotificationRepository @Inject constructor(
    @ApiAgent private val apiService: APIService
) : BaseRepository() {

    suspend fun registerNotification(notificationRequest: NotificationRequest): NetworkResponse<NotificationResponse, ErrorResponse> {
        return apiService.registerNotification(
            notificationRequest
        )
    }
}