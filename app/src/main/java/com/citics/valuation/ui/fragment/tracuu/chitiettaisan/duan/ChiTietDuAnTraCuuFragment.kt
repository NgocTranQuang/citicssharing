package com.citics.valuation.ui.fragment.tracuu.chitiettaisan.duan

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.citics.cagent.data.model.response.AssetDetailResponse
import com.citics.valuation.data.model.tai_san_chi_tiet.TypeDetail
import com.citics.valuation.data.model.tai_san_chi_tiet.TypeDuAn
import com.citics.valuation.customview.companion.*
import com.citics.valuation.extension.*
import com.citics.valuation.ui.activity.tracuu.ChiTietTaiSanTraCuuViewModel
import com.citics.valuation.ui.activity.tracuu.TimKiemCanHoActivity
import com.citics.valuation.ui.fragment.tracuu.chitiettaisan.base.BaseChiTietTaiSanTraCuuFragment
import com.citics.valuation.ui.fragment.tracuu.chitiettaisan.base.BaseChiTietTaiSanTraCuuViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChiTietDuAnTraCuuFragment :
    BaseChiTietTaiSanTraCuuFragment<BaseChiTietTaiSanTraCuuViewModel>() {
    override val viewModel: BaseChiTietTaiSanTraCuuViewModel by viewModels()
    private val activityViewModel: ChiTietTaiSanTraCuuViewModel by activityViewModels()

    private var vThongTinDuAnLayout: ThongTinDuAnLayout? = null
    private var vChiTietDuAn: ChiTietDuAnLayout? = null
    private var vTienIchNoiKhuLayout: TienIchNoiKhuLayout? = null


    override fun onConfigUI() {
        vThongTinDuAnLayout = ThongTinDuAnLayout(requireContext())
        vChiTietDuAn = ChiTietDuAnLayout(requireContext())
        vTienIchNoiKhuLayout = TienIchNoiKhuLayout(requireContext())
        super.onConfigUI()
    }

    override fun getListView(): MutableList<View?> {
        return mutableListOf(vThongTinDuAnLayout, vChiTietDuAn, vTienIchNoiKhuLayout)
    }

    override fun getDataDetail(): TypeDetail {
        return TypeDuAn()
    }

    override fun onObserverData() {
        super.onObserverData()
        dataListenerScope {
            activityViewModel.assetDetail.handleResponse(onLoading = {
                handleOnLoading()
            }, onFail = {
                handleOnFail(it)
            }) {
                binding.vStateFul.showContent()
                setBaseData(it?.data)
                setDataAsset(it)
                checkUiDataChange(it?.extra?.gia_tri_thay_doi)
            }
        }
    }

    private fun checkUiDataChange(map: Map<String, Any>?) {
        binding.lnHeaderTaiSan.setDataColor(map)
        vThongTinDuAnLayout?.setDataColor(map)
        vChiTietDuAn?.setDataColor(map)
        vTienIchNoiKhuLayout?.setDataColor(map)
    }

    override fun onClickListener() {
        super.onClickListener()
//        veThuaDatLayout?.onClickEditThongTin {
//            activity?.let {
//                NormalDialog.Builder(it).setTitle(R.string.chinh_sua_thong_tin_tua_dat)
//                    .setMessage(getString(R.string.des_dialog_chinh_sua_thong_tin_thua_dat))
//                    .setImage(R.drawable.ic_square_and_pencil)
//                    .setPositiveButton(R.string.btn_tien_hanh_bo_sung) { _: DialogInterface, _: Int ->
//                        findNavController().navigateWithAnimation(R.id.editNhaDat)
//                    }.setNegativeButton(R.string.btn_no_thanks) { _: DialogInterface, _: Int ->
//                    }.show(it.supportFragmentManager)
//            }
//        }
//        veCongTrinhLayout?.onBoSungCongTrinh {
//            activity?.let {
//                NormalDialog.Builder(requireContext())
//                    .setTitle(getString(R.string.cap_nhat_cong_trinh_xay_dung))
//                    .setMessage(getString(R.string.des_cap_nhat_cong_trinh_xay_dung))
//                    .setImage(R.drawable.ic_hammer)
//                    .setPositiveButton(R.string.tien_hanh_cap_nhat) { _: DialogInterface, _: Int ->
//                        congTrinhXayDungDetailForResult.launch(
//                            ChiTietCongTrinhActivity.newIntent(
//                                requireContext(),
//                                activityViewModel.assetDetail.value.data?.data
//                            )
//                        )
//                    }.setNegativeButton(R.string.btn_no_thanks) { _: DialogInterface, _: Int ->
//                    }.show(it.supportFragmentManager)
//            }
//        }
    }

    private fun setDataAsset(it: AssetDetailResponse?) {
        vThongTinDuAnLayout?.setValue(it?.data?.properties)
        vChiTietDuAn?.setValue(it?.data?.properties)
        vTienIchNoiKhuLayout?.setValue(it?.data?.properties)
    }


    override fun onClickSave() {

    }

    override fun onHeaderButtonClick() {
        startActivity(
            TimKiemCanHoActivity.newIntent(
                requireContext(),
                activityViewModel.assetDetail.value.data?.data?.id ?: ""
            )
        )
    }

}