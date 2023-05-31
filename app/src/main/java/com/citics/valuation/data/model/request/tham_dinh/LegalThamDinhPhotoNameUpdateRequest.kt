package com.citics.cagent.data.model.request.tham_dinh

import android.os.Parcelable
import com.citics.cagent.data.model.request.PhotoNameUpdateRequest
import kotlinx.parcelize.Parcelize

@Parcelize
data class LegalThamDinhPhotoNameUpdateRequest(
    var record_id: String? = null,
    var legal: MutableList<PhotoNameUpdateRequest>? = null
) : Parcelable
