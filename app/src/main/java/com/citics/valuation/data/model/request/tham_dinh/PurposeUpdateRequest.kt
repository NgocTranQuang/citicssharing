package com.citics.cagent.data.model.request.tham_dinh

import com.citics.cagent.data.model.response.LandPurposeResponse
import com.citics.cagent.data.model.response.PurposeUsingResponse

data class PurposeUpdateRequest(var record_id: String, var using_purpose: List<LandPurposeResponse>)
