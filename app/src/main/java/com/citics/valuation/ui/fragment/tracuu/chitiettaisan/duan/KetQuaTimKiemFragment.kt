package com.citics.valuation.ui.fragment.tracuu.chitiettaisan.duan

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.citics.cbank.R
import com.citics.cbank.databinding.FragmentCanHoKetQuaTimKiemBinding
import com.citics.valuation.adapter.tracuu.TimKiemNangCaoAdapter
import citics.sharing.customview.HeaderLayout
import citics.sharing.extension.makeLinks
import com.citics.valuation.ui.activity.tracuu.ChiTietCanHoActivity
import com.citics.valuation.ui.activity.tracuu.TimKiemCanHoViewModel
import com.citics.valuation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by ChinhQT on 10/11/2022.
 */
@AndroidEntryPoint
class KetQuaTimKiemFragment :
    BaseFragment<FragmentCanHoKetQuaTimKiemBinding, KetQuaTimKiemVM>(
        FragmentCanHoKetQuaTimKiemBinding::inflate
    ) {
    private val activityViewModel: TimKiemCanHoViewModel by activityViewModels()

    companion object {
        const val KEY_THAP = "KEY_THAP"
        const val KEY_TANG = "KEY_TANG"
        const val KEY_LOAI_CAN_HO = "KEY_LOAI_CAN_HO"
        const val KEY_DIEN_TICH_TIM_TUONG = "KEY_DIEN_TICH_TIM_TUONG"
        const val KEY_DIEN_TICH_THONG_THUY = "KEY_DIEN_TICH_THONG_THUY"
        fun getArgument(
            thap: String?,
            tang: String?,
            loaiCanHo: String?,
            dienTichTimTuong: String?,
            dienTichThongThuy: String?
        ): Bundle {
            return bundleOf(
                KEY_THAP to thap,
                KEY_TANG to tang,
                KEY_LOAI_CAN_HO to loaiCanHo,
                KEY_DIEN_TICH_TIM_TUONG to dienTichTimTuong,
                KEY_DIEN_TICH_THONG_THUY to dienTichThongThuy
            )

        }
    }


    override val viewModel: KetQuaTimKiemVM by viewModels()

//    private var projectID: String = ""
//    private var thap: String? = null
//    private var tang: String? = null
//    private var loaiCanHo: String? = null
//    private var dienTichTimTuong: String? = null
//    private var dienTichThongThuy: String? = null

    private var timKiemNangCaoAdapter: TimKiemNangCaoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        thap = arguments?.getString(KEY_THAP)
//        tang = arguments?.getString(KEY_TANG)
//        loaiCanHo = arguments?.getString(KEY_LOAI_CAN_HO)
//        dienTichTimTuong = arguments?.getString(KEY_DIEN_TICH_TIM_TUONG)
//        dienTichThongThuy = arguments?.getString(KEY_DIEN_TICH_THONG_THUY)
    }


    override fun onConfigUI() {
        super.onConfigUI()

        timKiemNangCaoAdapter = TimKiemNangCaoAdapter {
            startActivity(
                ChiTietCanHoActivity.newIntent(
                    requireContext(),
                    getHasMapMaCanHo(mch = it.text ?: "")
                )
            )
        }
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.dataList.layoutManager = layoutManager
        binding.dataList.adapter = timKiemNangCaoAdapter
        binding.tvDes.text = context?.getString(R.string.des_ketqua_tim_kiem_nang_cao)
        binding.tvDes.makeLinks((context?.getString(R.string.xem_kq_tuong_doi) ?: "") to {
            startActivity(
                ChiTietCanHoActivity.newIntent(
                    requireContext(),
                    activityViewModel.mapFilter.value ?: hashMapOf()
                )
            )
        })
    }


    override fun getHeaderLayout(): HeaderLayout {
        return binding.headerLayout
    }

    override fun onObserverData() {
        super.onObserverData()
        dataListenerScope {
            activityViewModel.mapFilter.collect {
                binding.statefulLayout.showLoading()
                it?.let {
                    viewModel.searchCanHoNangCao(it)
                }
            }
        }
        dataListenerScope {
            viewModel.canHoFilterAdvanceResponse.handleResponse(onFail = {
                showErrorDialog(it?.title, it?.message)
                binding.statefulLayout.showEmpty()
            }) {
                val temporary = it?.data?.content
                val size = temporary?.size ?: 0
                if (size > 0) {
                    timKiemNangCaoAdapter?.submitList(temporary)
                    binding.statefulLayout.showContent()
                } else {
                    binding.statefulLayout.showEmpty()
                }
            }
        }
    }

    private fun getHasMapMaCanHo(mch: String): HashMap<String, Any?> {
        val projectID = activityViewModel.mapFilter.value?.get("project_id")
        return hashMapOf("project_id" to projectID, "ma_can" to mch)
    }
}