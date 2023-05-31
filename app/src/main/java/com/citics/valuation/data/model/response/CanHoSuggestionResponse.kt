package com.citics.cagent.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by ChinhQT on 10/11/2022.
 */
@Parcelize
data class CanHoSuggestionResponse(val status: Boolean, val data: List<ContentItem>? = null) :
    Parcelable {
    @Parcelize
    data class ContentItem(val text: String? = null, val sub_text: String? = null) : Parcelable
}