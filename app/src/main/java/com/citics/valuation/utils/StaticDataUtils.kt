package com.citics.valuation.utils

import com.citics.cagent.data.model.response.DetailUsingPurpose
import com.citics.cagent.data.model.response.StaticCanHoResponse
import com.citics.cagent.data.model.response.StaticNhaDatResponse
import com.citics.cagent.data.model.response.TemplateResponse

object StaticDataUtils {
    // Data static api
    var staticNhaDat: StaticNhaDatResponse.Data? = null
    var staticCanHo: StaticCanHoResponse.Data? = null
    var staticFull: TemplateResponse.DataResponse? = null
    var staticMDSDD: List<DetailUsingPurpose>? = null

    var listLegalStaticNhaDat: List<String> = mutableListOf()
    var listLegalStaticCanHo: List<String> = mutableListOf()

}