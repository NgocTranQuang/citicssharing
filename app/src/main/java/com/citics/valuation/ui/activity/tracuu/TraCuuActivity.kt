package com.citics.valuation.ui.activity.tracuu

import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.paging.PagingData
import com.citics.valuation.adapter.cpoint.CPointMethodAdapter
import com.citics.valuation.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import com.citics.cbank.R
import com.citics.cbank.databinding.ActivityPhuongThucNhapDiemBinding
import com.citics.cbank.databinding.ActivityTracuuBinding

@AndroidEntryPoint
class TraCuuActivity :
    BaseActivity<ActivityTracuuBinding, TraCuuViewModel>() {
    override val viewModel: TraCuuViewModel by viewModels()

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

        startActivity(Intent(this, ChiTietNhaDatActivity::class.java))
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


    override val bindingInflater: (LayoutInflater) -> ActivityTracuuBinding
        get() = ActivityTracuuBinding::inflate
}