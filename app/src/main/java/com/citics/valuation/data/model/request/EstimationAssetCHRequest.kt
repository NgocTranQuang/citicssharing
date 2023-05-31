package com.citics.cagent.data.model.request

import com.citics.cagent.data.model.response.tham_dinh.Properties

/**
 * Created by ChinhQT on 07/11/2022.
 */
data class EstimationAssetCHRequest(
    val id: String, val loai_tai_san: String, val properties: Properties?
)