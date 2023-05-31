package com.citics.valuation.ui.activity.taikhoan

import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.paging.PagingData
import com.citics.valuation.adapter.cpoint.CPointMethodAdapter
import com.citics.valuation.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import com.citics.cbank.R
import com.citics.cbank.databinding.ActivityPhuongThucNhapDiemBinding
import com.citics.cbank.databinding.ActivityTaikhoanBinding

@AndroidEntryPoint
class TaiKhoanActivity :
    BaseActivity<ActivityTaikhoanBinding, TaiKhoanViewModel>() {
    override val viewModel: TaiKhoanViewModel by viewModels()

    private var adapter: CPointMethodAdapter? = null

    override fun onConfigUI() {
        super.onConfigUI()
        binding.vStateFul.showLoading()
        adapter = CPointMethodAdapter()
        binding.rvMethod.adapter = adapter
        binding.lnHeader.onBackClickListener = {
            finish()
        }
        binding.vStateFul.showEmpty()

    }

    override fun onObserverData() {
        super.onObserverData()
//        dataListenerScope {
//            viewModel.methods.handleResponse(onFail = {
//                binding.vStateFul.showEmpty()
//                showErrorDialog(it?.message, it?.title)
//            }) {
//                binding.vStateFul.showContent()
//                adapter?.submitData(PagingData.from(it?.data ?: listOf()))
//            }
//        }
    }


    override val bindingInflater: (LayoutInflater) -> ActivityTaikhoanBinding
        get() = ActivityTaikhoanBinding::inflate
}