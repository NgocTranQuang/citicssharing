package com.citics.valuation.ui.fragment.tracuu.chitiettaisan.sharing

import android.view.View
import androidx.fragment.app.viewModels
import com.citics.cbank.databinding.FragmentCvalueBinding
import com.citics.valuation.customview.HeaderLayout
import com.citics.valuation.ui.base.BaseFragment
import com.citics.valuation.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class CValueFragment :
    BaseFragment<FragmentCvalueBinding, CValueViewModel>(FragmentCvalueBinding::inflate) {
    override val viewModel: CValueViewModel by viewModels()

    override fun onConfigUI() {
        super.onConfigUI()
    }

    override fun getHeaderLayout(): HeaderLayout? {
        return binding.headerLayout
    }

    fun setUiForCanHo() {
        binding.lnNhaDat.visibility = View.GONE
        binding.lnCanHo.visibility = View.VISIBLE
    }

    fun setUiForNhatDat() {
        binding.lnNhaDat.visibility = View.VISIBLE
        binding.lnCanHo.visibility = View.GONE
    }
}