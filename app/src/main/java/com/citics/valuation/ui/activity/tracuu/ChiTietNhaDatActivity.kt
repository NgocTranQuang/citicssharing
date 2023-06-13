package com.citics.valuation.ui.activity.tracuu

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.citics.cbank.databinding.ActivityChitietnhadatBinding
import com.citics.valuation.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChiTietNhaDatActivity : BaseActivity<ActivityChitietnhadatBinding, ChiTietTaiSanTraCuuViewModel>() {
    override val viewModel: ChiTietTaiSanTraCuuViewModel by viewModels()
    override val bindingInflater: (LayoutInflater) -> ActivityChitietnhadatBinding
        get() = ActivityChitietnhadatBinding::inflate

    override fun onConfigUI() {
        super.onConfigUI()
        viewModel.getLandDetailByLatLng(10.78958187858458, 106.74536564472302)
    }
}