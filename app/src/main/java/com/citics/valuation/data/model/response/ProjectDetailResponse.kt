package com.citics.cagent.data.model.response

import android.os.Parcelable
import com.citics.cagent.data.model.response.base.BaseResponse
import com.citics.cagent.data.model.response.tham_dinh.Properties
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
class ProjectDetailResponse : BaseResponse<ProjectData, ExtraData>(), Parcelable

@Parcelize
data class ProjectData(
    @Json(name = "id") var id: String? = null,
    @Json(name = "loai_du_an") var level: String? = null,
    @Json(name = "tinh_trang") val level_title: String? = null,
    @Json(name = "properties") var properties: Properties.ThongTinDuAn? = null,
) : Parcelable