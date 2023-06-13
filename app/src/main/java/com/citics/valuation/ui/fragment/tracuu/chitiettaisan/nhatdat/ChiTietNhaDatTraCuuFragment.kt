package com.citics.valuation.ui.fragment.tracuu.chitiettaisan.nhatdat

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.citics.cagent.data.model.response.AssetDetailData
import com.citics.cagent.data.model.response.AssetDetailResponse
import com.citics.valuation.data.model.tai_san_chi_tiet.TypeDetail
import com.citics.cbank.R
import com.citics.valuation.customview.companion.VeCongTrinhLayout
import com.citics.valuation.data.model.tai_san_chi_tiet.TypeNhaDat
import com.citics.valuation.customview.companion.VeThuaDatLayout
import com.citics.valuation.customview.companion.ViTriTaiSanLayout
import com.citics.valuation.extension.*
import com.citics.valuation.ui.activity.chitietcongtrinh.ChiTietCongTrinhActivity
import com.citics.valuation.ui.activity.tracuu.ChiTietTaiSanTraCuuViewModel
import com.citics.valuation.ui.dialog.NormalDialog
import com.citics.valuation.ui.fragment.tracuu.chitiettaisan.base.BaseChiTietTaiSanTraCuuFragment
import com.citics.valuation.ui.fragment.tracuu.chitiettaisan.base.BaseChiTietTaiSanTraCuuViewModel
import com.citics.valuation.utils.KEY_BUNDLE_DATA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChiTietNhaDatTraCuuFragment :
    BaseChiTietTaiSanTraCuuFragment<BaseChiTietTaiSanTraCuuViewModel>() {
    override val viewModel: BaseChiTietTaiSanTraCuuViewModel by viewModels()
    private val activityViewModel: ChiTietTaiSanTraCuuViewModel by activityViewModels()

    private var vitriTaiSanLayout: ViTriTaiSanLayout? = null
    private var veThuaDatLayout: VeThuaDatLayout? = null
    private var veCongTrinhLayout: VeCongTrinhLayout? = null


    private val congTrinhXayDungDetailForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val intent = it.data
            onAddingCTXD(intent)
        }
    }

    private fun onAddingCTXD(intent: Intent?) {
        val bundle = intent?.getBundleExtra(KEY_BUNDLE_DATA)
        val asset = bundle?.getData<AssetDetailData>(ChiTietCongTrinhActivity.KEY_ASSET_DETAIL)
        val extra = bundle?.getString(ChiTietCongTrinhActivity.KEY_ASSET_EXTRA)
        val land = AssetDetailResponse().apply {
            this.data = asset
            this.extra = extra?.toObject()
        }
        activityViewModel.updateAssetDetail(land)
    }

    override fun onConfigUI() {
        vitriTaiSanLayout = ViTriTaiSanLayout(requireContext())
        veThuaDatLayout = VeThuaDatLayout(requireContext())
        veCongTrinhLayout = VeCongTrinhLayout(requireContext())
        super.onConfigUI()
    }

    override fun getListView(): MutableList<View?> {
        return mutableListOf(vitriTaiSanLayout, veThuaDatLayout, veCongTrinhLayout)
    }

    override fun getDataDetail(): TypeDetail {
        return TypeNhaDat()
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
        vitriTaiSanLayout?.setDataColor(map)
        veThuaDatLayout?.setDataColor(map)
        veCongTrinhLayout?.setDataColor(map)
    }

    override fun onClickListener() {
        super.onClickListener()
        veThuaDatLayout?.onClickEditThongTin {
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
        veCongTrinhLayout?.onBoSungCongTrinh {
            activity?.let {
                NormalDialog.Builder(requireContext())
                    .setTitle(getString(R.string.cap_nhat_cong_trinh_xay_dung))
                    .setMessage(getString(R.string.des_cap_nhat_cong_trinh_xay_dung))
                    .setImage(R.drawable.ic_hammer)
                    .setPositiveButton(R.string.tien_hanh_cap_nhat) { _: DialogInterface, _: Int ->
                        congTrinhXayDungDetailForResult.launch(
                            ChiTietCongTrinhActivity.newIntent(
                                requireContext(),
                                activityViewModel.assetDetail.value.data?.data
                            )
                        )
                    }.setNegativeButton(R.string.btn_no_thanks) { _: DialogInterface, _: Int ->
                    }.show(it.supportFragmentManager)
            }
        }
    }

    private fun setDataAsset(it: AssetDetailResponse?) {
        vitriTaiSanLayout?.setValue(it?.data?.properties)
        veThuaDatLayout?.setValue(it?.data?.properties)
        veCongTrinhLayout?.setValue(
            it?.data?.properties?.qhxd,
            it?.data?.properties?.cong_trinh,
            it?.extra?.gia_tri_thay_doi?.get("cong_trinh") as? Map<String, Any>
        )
    }


    override fun onClickSave() {

    }

    override fun onHeaderButtonClick() {

    }

}