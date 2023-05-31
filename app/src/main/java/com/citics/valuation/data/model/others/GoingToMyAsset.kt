package com.citics.valuation.data.model.others

import android.os.Parcelable
import com.citics.cagent.data.model.response.LandDetailResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class GoingToMyAsset(
    val loaiTaiSan: String,
    val myAssetId: String? = null,
    val landDetailResponse: LandDetailResponse? = null
) : Parcelable