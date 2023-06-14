package com.citics.valuation.ui.activity.tracuu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import citics.sharing.customview.TongGiaTriLayout
import citics.sharing.data.model.response.LandDTO
import com.citics.valuation.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import com.citics.cbank.databinding.ActivityChiTietDienTichThuaDatBinding
import com.citics.valuation.adapter.tracuu.VeThuaDatAdapter
import citics.sharing.extension.getListParcelable

@AndroidEntryPoint
class ChiTietDienTichThuaDatActivity :
    BaseActivity<ActivityChiTietDienTichThuaDatBinding, TraCuuViewModel>() {
    override val viewModel: TraCuuViewModel by viewModels()
    private var adapter: VeThuaDatAdapter? = null

    companion object {

        private const val KEY_DATA = "lstDat"
        private const val KEY_EXTRA = "extra"
        private const val KEY_DIEN_TICH = "dienTich"
        fun getArgument(dienTich: String, listDat: List<LandDTO>?): Bundle {
            return bundleOf(KEY_DATA to listDat?.toTypedArray(), KEY_DIEN_TICH to dienTich)
        }

        fun newIntent(context: Context, dienTich: String, listDat: List<LandDTO>?): Intent {
            val intent = Intent(context, ChiTietDienTichThuaDatActivity::class.java)
            intent.putExtra(KEY_EXTRA, getArgument(dienTich, listDat))
            return intent
        }
    }

    override fun onConfigUI() {
        super.onConfigUI()
        val bundle = intent?.getBundleExtra(KEY_EXTRA)
        val listData = bundle?.getListParcelable<LandDTO>(KEY_DATA)
        val dt = bundle?.getString(KEY_DIEN_TICH)
        adapter = VeThuaDatAdapter(
            this,
            listData?.toMutableList() ?: mutableListOf(),
            TongGiaTriLayout.TongGiaTriType.DIEN_TICH
        )
        binding.rvDetail.adapter = adapter
        binding.tvTongDienTich.setValue(dt)
        binding.headerLayout.onBackClickListener = { finish() }
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


    override val bindingInflater: (LayoutInflater) -> ActivityChiTietDienTichThuaDatBinding
        get() = ActivityChiTietDienTichThuaDatBinding::inflate
}