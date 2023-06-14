package com.citics.valuation.data.repository

import com.citics.cagent.data.model.request.NotificationRequest
import com.citics.cagent.data.model.response.NotificationResponse
import citics.sharing.service.customadapter.NetworkResponse
import com.citics.valuation.data.model.response.ErrorResponse
import com.citics.valuation.data.repository.base.BaseRepository
import citics.sharing.di.ApiAgent
import citics.sharing.service.APIService
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