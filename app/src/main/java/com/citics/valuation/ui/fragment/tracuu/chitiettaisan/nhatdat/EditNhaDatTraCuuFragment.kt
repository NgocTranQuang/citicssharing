package com.citics.valuation.ui.fragment.tracuu.chitiettaisan.nhatdat

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.citics.cagent.data.model.response.AssetDetailResponse
import com.citics.cbank.databinding.FragmentEditDetailLandBinding
import citics.sharing.extension.copyByJson
import com.citics.valuation.ui.activity.tracuu.ChiTietTaiSanTraCuuViewModel
import com.citics.valuation.ui.base.BaseChooserFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditNhaDatTraCuuFragment :
    BaseChooserFragment<FragmentEditDetailLandBinding, EditNhaDatViewModel>(
        FragmentEditDetailLandBinding::inflate
    ) {
    override val viewModel: EditNhaDatViewModel by viewModels()
    private val activityViewModel: ChiTietTaiSanTraCuuViewModel by activityViewModels()
    private var assetDetailCopy: AssetDetailResponse? = null

    override fun onConfigUI() {
        super.onConfigUI()
        binding.vChiTietThuaDat.setLauncher(startSingleChoiceForResult, startMultiChoiceForResult)
        binding.vHienTrangThuaDat.setLauncher(startSingleChoiceForResult, startMultiChoiceForResult)
    }

    override fun onClickListener() {
        super.onClickListener()
        binding.headerLayout.onBackClickListener = {
            findNavController().popBackStack()
        }
        binding.btnUpdate.setOnClickListener {
            updateData()
        }
    }

    override fun onObserverData() {
        super.onObserverData()
        dataListenerScope {
            activityViewModel.assetDetail.handleResponse { detail ->
                assetDetailCopy = detail.copyByJson()
                setDataViTri()
                binding.vAddress.setLoaiTaiSan(assetDetailCopy?.data?.loai_tai_san ?: "")
                binding.vAddress.setAddress(
                    assetDetailCopy?.data?.properties?.dia_chi1 ?: "",
                    assetDetailCopy?.data?.properties?.dia_chi2 ?: ""
                )
                binding.vChiTietThuaDat.setData(
                    assetDetailCopy?.data?.properties,
                    assetDetailCopy?.data?.usingPurpose,
                    assetDetailCopy?.data?.level
                )
                binding.vChiTietThuaDat.setOnChangeUsingPurpose {
                    binding.vHienTrangThuaDat.setListYeuToKhac(it)
                }
                binding.vHienTrangThuaDat.setData(
                    assetDetailCopy?.data?.properties,
                    assetDetailCopy?.data?.usingPurpose,
                    assetDetailCopy?.data?.level
                )
            }
        }
        dataListenerScope {
            viewModel.updateAssetDetailResponse.handleResponse {
                hideLoading()
                it?.let {
                    activityViewModel.updateAssetDetail(it)
                }
                onBackPress()
            }
        }
    }

    private fun setDataViTri() {
        assetDetailCopy?.data?.properties?.let { p ->
            binding.soToTF.setText(p.so_to) {
                p.so_to = it
            }
            binding.soThuaTF.setText(p.so_thua) {
                p.so_thua = it
            }

            binding.viDoTF.setText(p.vi_do) {
                p.vi_do = it
            }
            binding.kinhDoTF.setText(p.kinh_do) {
                p.kinh_do = it
            }
        }
    }

    private fun updateData() {
        val assetID = assetDetailCopy?.data?.id
        val loaiTaiSan = assetDetailCopy?.data?.loai_tai_san
        val usingPurpose = assetDetailCopy?.data?.usingPurpose
        val properties = assetDetailCopy?.data?.properties
        assetID?.let { id ->
            loaiTaiSan?.let { type ->
                if (properties != null) {
                    usingPurpose?.let { purposes ->
                        showLoading()
                        viewModel.estimationAsset(
                            id, type, properties, purposes
                        )
                    }
                }
            }
        }
    }

    override fun setDataSingleChoiceCallBack(intent: Intent?) {
        binding.vChiTietThuaDat.setDataSingleChoiceCallBack(intent)
        binding.vHienTrangThuaDat.setDataSingleChoiceCallBack(intent)
    }

    override fun setDataMultiChoiceCallBack(intent: Intent?) {
        binding.vChiTietThuaDat.setDataMultiChoiceCallBack(intent)
        binding.vHienTrangThuaDat.setDataMultiChoiceCallBack(intent)
    }


}