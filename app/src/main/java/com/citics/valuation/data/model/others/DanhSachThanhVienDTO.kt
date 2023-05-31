package com.citics.valuation.data.model.others

import com.citics.cagent.data.model.response.tham_dinh.RecordResponse

class DanhSachThanhVienDTO(val owner: RecordResponse.RecordData.RecordOwner) {
    var title: String? = null
    var showBottomDivider: Boolean = false
}