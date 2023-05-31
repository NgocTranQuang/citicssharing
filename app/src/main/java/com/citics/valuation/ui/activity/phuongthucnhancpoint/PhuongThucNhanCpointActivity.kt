package com.citics.valuation.ui.activity.phuongthucnhancpoint

import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.paging.PagingData
import com.citics.valuation.adapter.cpoint.CPointMethodAdapter
import com.citics.valuation.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import com.citics.cbank.R
import com.citics.cbank.databinding.ActivityPhuongThucNhapDiemBinding

@AndroidEntryPoint
class PhuongThucNhanCpointActivity :
    BaseActivity<ActivityPhuongThucNhapDiemBinding, PhuongThucNhanCPointViewModel>() {
    override val viewModel: PhuongThucNhanCPointViewModel by viewModels()

    private var adapter: CPointMethodAdapter? = null

    override fun onConfigUI() {
        super.onConfigUI()
        binding.vStateFul.showLoading()
        viewModel.getDepositMethods()
        adapter = CPointMethodAdapter()
        binding.rvMethod.adapter = adapter
        binding.lnHeader.onBackClickListener = {
            finish()
        }
    }

    override fun onObserverData() {
        super.onObserverData()
        dataListenerScope {
            viewModel.methods.handleResponse(onFail = {
                binding.vStateFul.showEmpty()
                showErrorDialog(it?.message, it?.title)
            }) {
                binding.vStateFul.showContent()
                adapter?.submitData(PagingData.from(it?.data ?: listOf()))
            }
        }
    }


    override val bindingInflater: (LayoutInflater) -> ActivityPhuongThucNhapDiemBinding
        get() = ActivityPhuongThucNhapDiemBinding::inflate
}