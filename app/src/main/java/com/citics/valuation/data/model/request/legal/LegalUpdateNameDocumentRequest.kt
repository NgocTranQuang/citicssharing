package com.citics.cagent.data.model.request.legal

import android.os.Parcelable
import com.citics.cagent.data.model.request.PhotoNameUpdateRequest
import kotlinx.parcelize.Parcelize

@Parcelize
data class LegalUpdateNameDocumentRequest(
    var id: String? = null,
    var files: MutableList<PhotoNameUpdateRequest>? = null,
    var compared_id: String? = null
    ) : Parcelable
