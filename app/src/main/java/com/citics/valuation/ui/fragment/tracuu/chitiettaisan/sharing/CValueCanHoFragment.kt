package com.citics.valuation.ui.fragment.tracuu.chitiettaisan.sharing

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.citics.cagent.data.model.response.DetailAdjustmentRates
import com.citics.cbank.R
import citics.sharing.extension.toShow
import com.citics.valuation.ui.activity.tracuu.ChiTietTaiSanTraCuuViewModel
import com.citics.valuation.utils.LoaiTaiSan
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CValueCanHoFragment : CValueFragment() {

    private val activityViewModel: ChiTietTaiSanTraCuuViewModel by activityViewModels()
    override fun onConfigUI() {
        super.onConfigUI()
        setUiForCanHo()
    }

    override fun onObserverData() {
        super.onObserverData()
        dataListenerScope {
            activityViewModel.assetDetail.handleResponse {
                setDataPriceValuer(it?.data?.adjustmentRates)
                if (it?.data?.loai_tai_san == LoaiTaiSan.CAN_HO.typeName) {
                    setUiForCanHo()
                } else {
                    setUiForNhatDat()
                }
                binding.tvNgayCapNhat.text =
                    context?.getString(
                        R.string.gia_tri_duoc_cap_nhat,
                        it?.data?.properties?.cap_nhat_cach_day
                    )
                if (it?.data?.properties?.cap_nhat_cach_day == null) {
                    binding.tvNgayCapNhat.visibility = View.GONE
                }
            }
        }
        dataListenerScope {
            viewModel.assetInfoWithCValue.handleResponse {
                hideLoading()
                it?.let {
                    activityViewModel.updateAssetDetail(it)
                }
                findNavController().popBackStack()
            }
        }
    }

    override fun onClickListener() {
        super.onClickListener()

    }


    private fun setDataPriceValuer(data: DetailAdjustmentRates?) {
        binding.tvCValue.setValue(data?.gia_tri_tham_dinh?.toShow())
        binding.tvBienDoGiaNhoNhat.setValue(data?.bien_do_gia_thap_nhat?.toShow())
        binding.tvBienDoGiaLonNhat.setValue(data?.bien_do_gia_cao_nhat?.toShow())
        binding.vGiaTriCanHo.setValue(data?.gia_tri_tham_dinh?.toShow())
        binding.vDonGiaCanHo.setValue(data?.don_gia?.toShow())
    }

}