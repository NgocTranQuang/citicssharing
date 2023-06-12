package com.citics.valuation.ui.fragment.tracuu.chitiettaisan.duan

import android.content.DialogInterface
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.citics.cagent.data.model.response.AssetDetailResponse
import com.citics.valuation.data.model.tai_san_chi_tiet.TypeCanHo
import com.citics.valuation.data.model.tai_san_chi_tiet.TypeDetail
import com.citics.cbank.R
import com.citics.valuation.customview.companion.ChiTietCanHoLayout
import com.citics.valuation.extension.setDataColor
import com.citics.valuation.ui.activity.tracuu.ChiTietNhaDatViewModel
import com.citics.valuation.ui.dialog.NormalDialog
import com.citics.valuation.ui.fragment.tracuu.chitiettaisan.base.BaseChiTietTaiSanTraCuuFragment
import com.citics.valuation.ui.fragment.tracuu.chitiettaisan.base.BaseChiTietTaiSanTraCuuViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChiTietCanHoTraCuuFragment :
    BaseChiTietTaiSanTraCuuFragment<BaseChiTietTaiSanTraCuuViewModel>() {
    override val viewModel: BaseChiTietTaiSanTraCuuViewModel by viewModels()
    private val activityViewModel: ChiTietNhaDatViewModel by activityViewModels()

    private var vChiTietCanHo: ChiTietCanHoLayout? = null


    override fun onConfigUI() {
        vChiTietCanHo = ChiTietCanHoLayout(requireContext())
        super.onConfigUI()
    }

    override fun getListView(): MutableList<View?> {
        return mutableListOf(vChiTietCanHo)
    }

    override fun getDataDetail(): TypeDetail {
        return TypeCanHo()
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
        vChiTietCanHo?.setDataColor(map)
    }

    override fun onClickListener() {
        super.onClickListener()
        vChiTietCanHo?.onClickEditDetail = {
            activity?.let {
                NormalDialog.Builder(it).setTitle(R.string.chinh_sua_thong_tin_tua_dat)
                    .setMessage(getString(R.string.des_dialog_chinh_sua_thong_tin_thua_dat))
                    .setImage(R.drawable.ic_square_and_pencil)
                    .setPositiveButton(R.string.btn_tien_hanh_bo_sung) { _: DialogInterface, _: Int ->
                        findNavController().navigateWithAnimation(R.id.editNhaDat)
                    }.setNegativeButton(R.string.btn_no_thanks) { _: DialogInterface, _: Int ->
                    }.show(it.supportFragmentManager)
            }
        }
    }

    private fun setDataAsset(it: AssetDetailResponse?) {
        vChiTietCanHo?.setValue(it?.data?.properties)
    }


    override fun onClickSave() {

    }

    override fun onHeaderButtonClick() {

    }

}