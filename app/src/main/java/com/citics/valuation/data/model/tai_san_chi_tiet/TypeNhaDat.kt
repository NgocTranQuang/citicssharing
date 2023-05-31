package com.citics.cagent.data.model.tai_san_chi_tiet

import com.citics.cbank.R


class TypeNhaDat : TypeDetail {
    override fun getImageTitleResource(): Int {
        return R.drawable.ic_home_white
    }

    override fun getTitle(): Int {
        return R.string.nha_dat
    }

    override fun getTitleButton(): Int {
        return R.string.them_vao_tai_san_cua_toi
    }

    override fun getDrawableButton(): Int {
        return R.drawable.ic_tray
    }

    override fun getListTabBarTitle(): MutableList<Int> {
        return mutableListOf(R.string.thong_tin_gia, R.string.ve_thua_dat, R.string.ve_cong_trinh)
    }
}