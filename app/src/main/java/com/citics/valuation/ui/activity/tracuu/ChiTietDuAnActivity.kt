package com.citics.valuation.ui.activity.tracuu

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.citics.cbank.databinding.ActivityChitietduanBinding
import com.citics.cbank.databinding.ActivityChitietnhadatBinding
import com.citics.valuation.ui.base.BaseActivity
import com.citics.valuation.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChiTietDuAnActivity : BaseActivity<ActivityChitietduanBinding, ChiTietNhaDatViewModel>() {
    override val viewModel: ChiTietNhaDatViewModel by viewModels()
    override val bindingInflater: (LayoutInflater) -> ActivityChitietduanBinding
        get() = ActivityChitietduanBinding::inflate

    override fun onConfigUI() {
        super.onConfigUI()
        viewModel.getProjectDetail("12")
    }
}