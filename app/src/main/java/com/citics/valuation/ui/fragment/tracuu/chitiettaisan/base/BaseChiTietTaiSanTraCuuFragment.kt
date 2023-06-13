package com.citics.valuation.ui.fragment.tracuu.chitiettaisan.base

import android.annotation.SuppressLint
import android.view.View
import androidx.navigation.fragment.findNavController
import com.ahmadhamwi.tabsync.TabbedListMediator
import com.citics.cagent.data.model.response.AssetDetailData
import com.citics.cagent.data.model.response.DetailAdjustmentRates
import com.citics.valuation.data.model.tai_san_chi_tiet.TypeDetail
import com.citics.cbank.R
import com.citics.cbank.databinding.FragmentDetailTaiSanBaseBinding
import com.citics.valuation.adapter.tracuu.ChiTietTaiSanAdapter
import com.citics.valuation.customview.HeaderLayout
import com.citics.valuation.data.model.response.ErrorResponse
import com.citics.valuation.extension.setHtml
import com.citics.valuation.extension.toShow
import com.citics.valuation.extension.toShowTime
import com.citics.valuation.ui.base.BaseFragment
import com.citics.valuation.utils.LoaiTaiSan

abstract class BaseChiTietTaiSanTraCuuFragment<VM : BaseChiTietTaiSanTraCuuViewModel> :
    BaseFragment<FragmentDetailTaiSanBaseBinding, VM>(FragmentDetailTaiSanBaseBinding::inflate) {

    private var chiTietTaiSanAdapter: ChiTietTaiSanAdapter? = null
    private var assetDetailData: AssetDetailData? = null

    override fun onConfigUI() {
        super.onConfigUI()
        bindDataView()
        initMediator()
    }

    override fun onObserverData() {
        super.onObserverData()
        dataListenerScope {
            viewModel.voteResponse.handleResponseOnce(onLoading = {
                assetDetailData?.id?.let {
                    viewModel.vote(it, null)
                }
            }) {
                handleUiVote(it?.data?.is_voted)
            }
        }
    }

    override fun getHeaderLayout(): HeaderLayout? {
        return binding.headerLayout
    }

    override fun onClickListener() {
        super.onClickListener()
        binding.cvLike.setOnClickListener {
            onClickLike()
        }
        binding.lnActionHeader.setOnClickListener {
            onHeaderButtonClick()
        }
        binding.lnTimCanHo.setOnClickListener {
            onHeaderButtonClick()
        }
        binding.vAddress.setOnFavouriteClick {
            onClickSave()
        }
        binding.btnBottom.setOnClickListener {
            onHeaderButtonClick()
        }
        binding.tvCValue.setOnClickListener {
            findNavController().navigateWithAnimation(R.id.cvaluefragment)
        }
    }

    private fun handleUiVote(voted: Boolean?) {
        if (voted == true) {
            showUiVoted()
        } else {
            showUiChuaVote()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun showUiChuaVote() {
        binding.tvTitleLike.setTextColor(requireContext().getColor(R.color.color_text))
        binding.tvTitleLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like, 0, 0, 0);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun showUiVoted() {
        binding.tvTitleLike.setTextColor(requireContext().getColor(R.color.color_icon_tint))
        binding.tvTitleLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_liked, 0, 0, 0);
    }


    fun setBaseData(data: AssetDetailData?) {
        this.assetDetailData = data
        binding.vAddress.setLoaiTaiSan(data?.loai_tai_san ?: "")
        setDataHeader(data?.properties?.danh_sach_hinh_anh?.map { it.photo_url ?: "" }
            ?.toMutableList(),
            data?.properties?.dia_chi1,
            data?.properties?.dia_chi2)
        if (data?.loai_tai_san == LoaiTaiSan.NHA_DAT.typeName || data?.loai_tai_san == LoaiTaiSan.CAN_HO.typeName) {
            // Nha dat or can ho
            binding.lnHeaderDuAn.visibility = View.GONE
            binding.lnHeaderTaiSan.visibility = View.VISIBLE
            setNgayCapNhatGia(data.properties?.ngay_cap_nhat_gia)
            setDataPriceValuer(data.adjustmentRates)
            if (data.loai_tai_san == LoaiTaiSan.NHA_DAT.typeName) {
                if (data.usingPurpose?.firstOrNull { purpose -> purpose.ignore_pricing == true } != null) {
                    // Đất thuộc dạng không được phép sử dụng (ví dụ như đất quân đội, đất cấm...)
                    onDatDacThu()
                } else {
                    if ((data.adjustmentRates?.don_gia ?: 0) == 0L) {
                        onDatInvalid()
                    }
                }
            }
        } else {
            // Du an
            binding.lnHeaderDuAn.visibility = View.VISIBLE
            binding.lnHeaderTaiSan.visibility = View.GONE
            binding.tvGiaTrungBinhDuAn.text = data?.properties?.gia_mo_ban_text
            if (data?.properties?.ngay_cap_nhat_gia != null) {
                binding.tvNgayCapNhatDuAn.text = getString(
                    R.string.citics_cap_nhat,
                    data.properties?.ngay_cap_nhat_gia?.toShowTime()
                )
            } else {
                binding.tvNgayCapNhatDuAn.visibility = View.GONE
            }
            binding.tvDonGiaTB.setValue(data?.properties?.don_gia_trung_binh.toShow())
            binding.tvNgayCapNhatDGTBDUAn.text = getString(
                R.string.citics_cap_nhat,
                data?.properties?.ngay_cap_nhat_don_gia_trung_binh?.toShowTime()
            )
            if (data?.properties?.ngay_cap_nhat_don_gia_trung_binh == null) {
                binding.tvNgayCapNhatDGTBDUAn.visibility = View.GONE
            }
        }

    }

    private fun setNgayCapNhatGia(ngayCapNhatGia: Long?) {
        ngayCapNhatGia?.let { priceUpdatingDate ->
            binding.tvNgayCapNhat.visibility = View.VISIBLE
            binding.tvNgayCapNhat.text = priceUpdatingDate.toShowTime()
        } ?: kotlin.run {
            binding.tvNgayCapNhat.visibility = View.GONE
        }
    }

    private fun setDataPriceValuer(data: DetailAdjustmentRates?) {
        binding.tvCValue.setValue(data?.gia_tri_tham_dinh?.toShow())
        binding.tvBienDoGiaNhoNhat.setValue(data?.bien_do_gia_thap_nhat?.toShow())
        binding.tvBienDoGiaLonNhat.setValue(data?.bien_do_gia_cao_nhat?.toShow())
        // Dự án
        binding.tvGiaTrungBinhDuAn.setValue(data?.don_gia?.toShow())
    }

    private fun setDataHeader(
        danhSachHinhAnh: MutableList<String>?, address1: String? = null, address2: String? = null
    ) {
        setDataSlider(danhSachHinhAnh)
        binding.vAddress.setAddress(address1 ?: "", address2 ?: "")
    }

    private fun setDataSlider(listImage: MutableList<String>?) {
        val newListImage = listImage ?: mutableListOf()
        if (newListImage.size == 0) {
            binding.imagesList.visibility = View.GONE
        } else {
            binding.imagesList.setData(newListImage)
            binding.imagesList.visibility = View.VISIBLE
        }
    }

    private fun initMediator() {
        binding.rvDetail.let { rv ->
            binding.contentTab.let { tab ->
                getDataDetail().getListTabBarTitle()?.map { getString(it) }?.indices?.toList()
                    ?.let {
                        TabbedListMediator(
                            rv,
                            tab,
                            it,
                            true
                        ).attach()
                    } ?: kotlin.run {
                    binding.contentTab.visibility = View.GONE
                }
            }
        }
    }

    private fun bindDataView() {
        getDataDetail().let {
            binding.tvActionHeader.text = getString(it.getTitleButton())
            binding.btnBottom.setTitleButton(getString(it.getTitleButton()))
            binding.tvActionHeader.setDrawableStartTextview(it.getDrawableButton())
            it.getListTabBarTitle()?.forEach { index ->
                binding.contentTab.newTab().setText(getString(index)).let { tab ->
                    binding.contentTab.addTab(tab)
                }
            }
        }
        chiTietTaiSanAdapter =
            ChiTietTaiSanAdapter(requireContext(), getListView().filterNotNull().toMutableList())
        binding.rvDetail.adapter = chiTietTaiSanAdapter

    }

    open fun handleOnLoading() {
        binding.vStateFul.showLoading()
    }

    open fun handleOnFail(it: ErrorResponse?) {
        showErrorDialog(it?.title, it?.message)
        binding.vStateFul.showEmpty()
    }


    private fun onDatInvalid() {
        binding.tvNoInfo.visibility = View.VISIBLE
        binding.tvNoInfo.setHtml(context?.getString(R.string.des_khong_co_ttg))
        binding.lnHeaderTaiSan.visibility = View.GONE

    }

    private fun onDatDacThu() {
        binding.tvNoInfo.visibility = View.VISIBLE
        binding.lnHeaderTaiSan.visibility = View.GONE
        binding.tvNoInfo.setHtml(context?.getString(R.string.des_dat_dac_thu))
    }

    private fun onClickLike() {
        assetDetailData?.id?.let {
            viewModel.vote(it, viewModel.voteResponse.value.data?.data?.is_voted)
        }
    }


    /*Chi tiết sẽ là list các view để đổ vào recyclerview*/
    abstract fun getListView(): MutableList<View?>

    /*Thông tin đặc trưng của từng loại tài sản*/
    abstract fun getDataDetail(): TypeDetail

    abstract fun onClickSave()
    abstract fun onHeaderButtonClick()


}