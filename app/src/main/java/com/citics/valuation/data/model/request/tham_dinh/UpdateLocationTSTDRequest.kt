package com.citics.cagent.data.model.request.tham_dinh

import com.citics.cagent.data.model.response.tham_dinh.Properties

data class UpdateLocationTSTDRequest(
    val properties: Properties? = null,
    val record_id: String? = null,
    val nha_pho: NhaPho? = null,
    val can_ho: CanHo? = null
)
