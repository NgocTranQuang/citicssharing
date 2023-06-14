package com.citics.valuation.ui.fragment.sharing

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import citics.sharing.customview.CiticsTextField
import citics.sharing.customview.HeaderLayout
import citics.sharing.extension.*
import com.citics.cagent.data.model.request.EstimationConstructionRequest
import com.citics.cagent.data.model.response.CongTrinh
import com.citics.cagent.data.model.response.StaticNhaDatResponse
import com.citics.cbank.R
import com.citics.cbank.databinding.FragmentBoSungCongTrinhBinding
import com.citics.valuation.adapter.list_cong_trinh.RoomsAdapter
import com.citics.valuation.data.model.others.ChooserItem
import com.citics.valuation.data.model.others.SelectorItem
import com.citics.valuation.data.model.others.SingleChoiceData
import com.citics.valuation.ui.activity.chitietcongtrinh.ChiTietCongTrinhViewModel
import com.citics.valuation.ui.activity.chitietcongtrinh.ThemPhongActivity
import com.citics.valuation.ui.base.BaseChooserFragment
import com.citics.valuation.ui.fragment.tracuu.chitiettaisan.nhatdat.EditNhaDatViewModel
import com.citics.valuation.utils.KEY_BUNDLE_DATA
import com.citics.valuation.utils.LevelType
import com.citics.valuation.utils.StaticDataUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoSungCongTrinhFragment :
    BaseChooserFragment<FragmentBoSungCongTrinhBinding, BoSungCongTrinhViewModel>(
        FragmentBoSungCongTrinhBinding::inflate
    ) {


    override val viewModel: BoSungCongTrinhViewModel by viewModels()
    private val editViewModel: EditNhaDatViewModel by viewModels()
    private val activityViewModel: ChiTietCongTrinhViewModel by activityViewModels()

    private val roomForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val intent = it.data
            onAddingNewRoom(intent)
        }
    }


    private var listLoaiCongTrinh: MutableList<String> =
        StaticDataUtils.staticNhaDat?.loaiCongTrinh?.toMutableList() ?: mutableListOf()
    private var danhMucCongTrinh: List<StaticNhaDatResponse.DanhMucCongTrinh>? =
        StaticDataUtils.staticNhaDat?.danhMucCongTrinh
    private var danhMucCongTrinhSelected: List<StaticNhaDatResponse.DanhMucCongTrinh>? =
        mutableListOf()

    private var congtrinh: CongTrinh = CongTrinh()
    private var validation: List<String> = mutableListOf()
    private var level: String? = null

    companion object {
        const val KEY_NHA_XUONG = "Nhà xưởng"
        const val KEY_NHA_CAO_TANG = "Nhà cao tầng"
    }

    override fun onObserverData() {
        super.onObserverData()
        dataListenerScope {
            activityViewModel.asset.collect {
                level = it?.level
            }
        }
        dataListenerScope {
            activityViewModel.congTrinh.collect { congTrinh ->
                this@BoSungCongTrinhFragment.congtrinh = congTrinh.copyByJson() ?: CongTrinh()
                getHeaderLayout()?.setTitle(
                    congTrinh.label ?: getString(R.string.bo_sung_cong_trinh_xay_dung)
                )
                handleUILoaiCongTrinh()
                if (level != LevelType.TRA_CUU.type) {
                    tinhTongDienTichTangHam()
                    tinhTongDienTichTangNoi()
                    calculateDonGiaXayDung()
                }
                binding.lnScroll.setColorError(validation.toMutableList())
                setDataUI(congtrinh)

            }
        }


        dataListenerScope {
            viewModel.estimation.handleResponse {
                binding.vDonGiaXayDung.setValue(it?.data?.don_gia.toShow())
                binding.vClcl.setValue(it?.data?.clcl.toShow())
            }
        }
        dataListenerScope {
            editViewModel.updateAssetDetailResponse.handleResponse {
                it?.data?.let {
                    hideLoading()
                    activityViewModel.setAsset(it)
                }
                activityViewModel.setExtraData(it?.extra)
                findNavController().popBackStack()
            }
        }
    }

    private fun setDataUI(congtrinh: CongTrinh) {
        binding.btnChonLoaiCongTrinhXd.setText(congtrinh.loaiCongTrinh ?: "")
        binding.vTongDienTichXayDung.setValue(congtrinh.dien_tich_san?.toShow())
        binding?.tfTongDienTich?.setText(congtrinh.dien_tich_san) {
            congtrinh.dien_tich_san = it
        }

        // Tang tren
        binding.vSoTang?.setText(congtrinh.tangTren) {
            congtrinh.tangTren = it
            onTinhListKetCau()
            calculateDonGiaXayDung()
        }
        binding.vDaCongNhan?.setText(
            congtrinh.dien_tich_san_xay_dung_cac_tang_tren_phqh
        ) {
            congtrinh.dien_tich_san_xay_dung_cac_tang_tren_phqh = it
            tinhTongDienTichTangNoi()
        }
        binding.vCPCHC?.setText(
            congtrinh.dien_tich_san_xay_dung_cac_tang_tren_incomplete
        ) {
            congtrinh.dien_tich_san_xay_dung_cac_tang_tren_incomplete = it
            tinhTongDienTichTangNoi()
        }
        binding.vKhongCoPhep?.setText(
            congtrinh.dien_tich_san_xay_dung_cac_tang_tren_vpqh
        ) {
            congtrinh.dien_tich_san_xay_dung_cac_tang_tren_vpqh = it
            tinhTongDienTichTangNoi()
        }
        // Tang ham
        binding.vSoTangHam?.setText(congtrinh.tangHam ?: 0) {
            congtrinh.tangHam = it
            onTinhListKetCau()
        }
        binding.vDaCongNhanTangham?.setText(
            congtrinh.dien_tich_san_xay_dung_tang_ham_phqh
        ) {
            congtrinh.dien_tich_san_xay_dung_tang_ham_phqh = it
            tinhTongDienTichTangHam()
        }
        binding.vCPCHCTangHam?.setText(
            congtrinh.dien_tich_san_xay_dung_tang_ham_incomplete
        ) {
            congtrinh.dien_tich_san_xay_dung_tang_ham_incomplete = it
            tinhTongDienTichTangHam()
        }
        binding.vKhongCoPhepTangHam?.setText(
            congtrinh.dien_tich_san_xay_dung_tang_ham_vpqh
        ) {
            congtrinh.dien_tich_san_xay_dung_tang_ham_vpqh = it
            tinhTongDienTichTangHam()
        }

        binding.vCapNha?.setText(congtrinh.cap_nha ?: "")
        binding.vKetCau?.setText(congtrinh.ket_cau_nha ?: "")
        binding.vMoTaCongTrinh?.setText(congtrinh.mo_ta_cong_trinh ?: "") {
            congtrinh.mo_ta_cong_trinh = it
        }
        binding.vNamXayDung?.setText(congtrinh.nam_xay_dung) {
            congtrinh.nam_xay_dung = it
            calculateDonGiaXayDung()
        }
        binding.vNamSuaChua?.setText(congtrinh.nam_sua_chua) {
            congtrinh.nam_sua_chua = it
            calculateDonGiaXayDung()
        }
        binding.vPhongNgu?.setText(congtrinh.so_phong_ngu) {
            congtrinh.so_phong_ngu = it
        }
        binding.vPhongVeSinh?.setText(congtrinh.so_wc) {
            congtrinh.so_wc = it
        }
        binding.vPhongBep?.setText(congtrinh.so_phong_bep) {
            congtrinh.so_phong_bep = it
        }
        binding.vPhongKhach?.setText(congtrinh.so_phong_khach) {
            congtrinh.so_phong_khach = it
        }
        if ((congtrinh.extra_rooms?.size ?: 0) == 0) {
            binding.vDotPhongBep?.hideBottomLine()
        } else {
            binding.vDotPhongBep?.showBottomLine()
        }
        binding.rvRooms?.adapter =
            RoomsAdapter(requireContext(), congtrinh.extra_rooms ?: mutableListOf())
        binding.vCSKTHT?.setListSelected(congtrinh.co_so_ha_tang_ky_thuat.toListSelector())
        binding.vSoSanhVoiThucTe?.setText(
            congtrinh.so_sanh_thuc_te_co_so_ha_tang_ky_thuat ?: ""
        )
        binding.vGhiChu?.setText(
            congtrinh.so_sanh_thuc_te_co_so_ha_tang_ky_thuat_khac ?: ""
        ) {
            congtrinh.so_sanh_thuc_te_co_so_ha_tang_ky_thuat_khac = it
        }
        binding.vCSKTHT.setLauncher(startSingleChoiceForResult, startMultiChoiceForResult)
        binding.vCSKTHT.setListChoice(StaticDataUtils.staticFull?.co_so_ha_tang_ky_thuat?.map { item ->
            SelectorItem(
                id = item.key, name = item.title
            )
        } ?: mutableListOf())
        onTinhListKetCau()
        handleUiByLevel()
    }

    override fun onResume() {
        super.onResume()

    }

    private fun handleUILoaiCongTrinh() {
        if (TextUtils.isEmpty(congtrinh?.loaiCongTrinh)) {
            binding.tvWarning?.visibility = View.VISIBLE
            binding.lnContent?.visibility = View.GONE
            binding.btnCapNhat?.disableButton()
        } else {
            binding.tvWarning?.visibility = View.GONE
            binding.lnContent?.visibility = View.VISIBLE
            binding.btnCapNhat?.enableButton()

            if (congtrinh?.loaiCongTrinh == KEY_NHA_XUONG) {
                binding.lnTangHam?.visibility = View.GONE
                binding.lnCauTrucPhong?.visibility = View.GONE
            } else {
                binding.lnTangHam?.visibility = View.VISIBLE
                binding.lnCauTrucPhong?.visibility = View.VISIBLE

            }
            if (congtrinh?.loaiCongTrinh == KEY_NHA_CAO_TANG || congtrinh?.loaiCongTrinh == KEY_NHA_XUONG) {
                binding.lnCauTrucPhong?.visibility = View.GONE
            } else {
                binding.lnCauTrucPhong?.visibility = View.VISIBLE

            }
        }
    }

    private fun onTinhListKetCau() {

        val loaiCongTrinh = binding.btnChonLoaiCongTrinhXd?.getText().toString()
        val soTangNoi = binding.vSoTang?.getText().toString().toIntOrNull() ?: 0
        val soTangHam = binding.vSoTangHam?.getText().toString().toIntOrNull() ?: 0
        val tongSoTang = soTangHam + soTangNoi
        val listDanhMucLoaiCongTrinh = danhMucCongTrinh?.filter {
            it.houseKind == loaiCongTrinh
        }
        val listDanhMucGTE = listDanhMucLoaiCongTrinh?.filter {
            it.numOfFloor?.contains("GTE") == true
        }?.filter {
            (it.numOfFloor?.replace("GTE", "")?.toIntOrNull() ?: 0) <= tongSoTang
        }?.toMutableList() ?: mutableListOf()

        val listDanhMucLTE = listDanhMucLoaiCongTrinh?.filter {
            it.numOfFloor?.contains("LTE") == true
        }?.filter {
            (it.numOfFloor?.replace("LTE", "")?.toIntOrNull() ?: 0) >= tongSoTang
        }?.toMutableList() ?: mutableListOf()
        val listDanhMucEql =
            listDanhMucLoaiCongTrinh?.filter {
                (it.numOfFloor?.toIntOrNull() ?: 0) == tongSoTang
            }
                ?.toMutableList() ?: mutableListOf()

        listDanhMucEql.addAll(listDanhMucGTE)
        listDanhMucEql.addAll(listDanhMucLTE)
        danhMucCongTrinhSelected = listDanhMucEql.filter { it.prototype != null }
    }

    override fun getHeaderLayout(): HeaderLayout? {
        return binding.headerLayout
    }

    @SuppressLint("SetTextI18n")
    private fun tinhTongDienTichTangNoi() {
        val item1 = congtrinh?.dien_tich_san_xay_dung_cac_tang_tren_phqh ?: 0.0
        val item2 = congtrinh?.dien_tich_san_xay_dung_cac_tang_tren_incomplete ?: 0.0
        val item3 = congtrinh?.dien_tich_san_xay_dung_cac_tang_tren_vpqh ?: 0.0
        val total = item1 + item2 + item3
        congtrinh?.dien_tich_san_xay_dung_cac_tang_tren = total
        binding.tvDTTN?.text = "${total.toShow()} ${getString(R.string.m2)}"
        tinhTongDienTich()
    }

    private fun tinhTongDienTich() {

        val total1 = congtrinh?.dien_tich_san_xay_dung_cac_tang_tren ?: 0.0
        val total2 = congtrinh?.dien_tich_san_xay_dung_tang_ham ?: 0.0

        val total = total1 + total2
        binding.vTongDienTichXayDung.setValue(total.toShow())
        congtrinh.dien_tich_san = total.toFloat()
        binding.tfTongDienTich?.setText(congtrinh.dien_tich_san)

    }

    @SuppressLint("SetTextI18n")
    private fun tinhTongDienTichTangHam() {
        val item1 = congtrinh?.dien_tich_san_xay_dung_tang_ham_phqh ?: 0.0
        val item2 = congtrinh?.dien_tich_san_xay_dung_tang_ham_incomplete ?: 0.0
        val item3 = congtrinh?.dien_tich_san_xay_dung_tang_ham_vpqh ?: 0.0
        val total = item1 + item2 + item3
        congtrinh?.dien_tich_san_xay_dung_tang_ham = total
        binding.tvDTTNTangHam?.text = "${total.toShow()} ${getString(R.string.m2)}"
        tinhTongDienTich()
    }

    override fun onClickListener() {
        super.onClickListener()
        binding.btnChonLoaiCongTrinhXd.onClickListener = {
            goToSingleChoiceScreen(
                R.string.loai_cong_trinh,
                listLoaiCongTrinh.toListChooser().toMutableList(),
                congtrinh?.loaiCongTrinh
            )
        }

        binding.btnAddNewRoom.setOnClickListener {
            roomForResult.launch(ThemPhongActivity.newIntent(requireContext(), null))
        }
        binding.vCapNha?.setOnClickListener {
            goToSingleChoiceScreen(
                R.string.cap_nha,
                StaticDataUtils.staticNhaDat?.houseLevel?.toListChooser() ?: mutableListOf(),
                binding.vCapNha?.getText().toString()
            )
        }
        binding.vKetCau?.setOnClickListener {
            if ((binding.vSoTang?.getText().toString().toIntOrNull() ?: 0) == 0) {
                showError(getString(R.string.xvlnstdtt))
                return@setOnClickListener
            }
            goToSingleChoiceScreen(
                SingleChoiceData(
                    R.string.ket_cau,
                    danhMucCongTrinhSelected?.map { it.prototype }.toListChooser(),
                    binding.vKetCau.getText().toString(),
                    hasOther = false,
                    messageNodata = getString(R.string.kckcph)
                )
            )
        }
        binding.vSoSanhVoiThucTe.setOnClickListener {
            goToSingleChoiceScreen(
                R.string.so_sanh_voi_thuc_te,
                StaticDataUtils.staticFull?.so_sanh_thuc_te_co_so_ha_tang_ky_thuat?.map {
                    ChooserItem(
                        id = it.key, name = it.title
                    )
                }?.toMutableList() ?: mutableListOf(),
                binding.vSoSanhVoiThucTe.getText().toString()
            )
        }
        binding.btnCapNhat.onClickListener {
            activityViewModel.asset.value?.properties?.let { pro ->
                val assetId = pro.id
                val loaiTaiSan = pro.loai_tai_san
                val usingPurpose = activityViewModel.asset.value?.usingPurpose

                // Add Công trình vào
                val index = pro.cong_trinh?.indexOfFirst { it.id == congtrinh?.id } ?: -1
                if (index == -1) {
                    // Tạo mới công trình
                    congtrinh?.let {
                        pro.cong_trinh?.add(it)
                    }
                } else {
                    congtrinh?.let {
                        pro.cong_trinh?.set(index, it)
                    }
                }
                showLoading()
                editViewModel.estimationAsset(
                    assetId ?: "",
                    loaiTaiSan ?: "",
                    pro,
                    usingPurpose ?: mutableListOf()
                )
            }
        }
    }

    override fun onResultMultiChoice(key: Int, list: MutableList<SelectorItem>) {
        when (key) {
            R.string.co_so_ha_tang_ky_thuat -> {
                congtrinh?.co_so_ha_tang_ky_thuat = list.map { it.name }
            }
        }
        congtrinh?.let {
            setDataUI(it)
        }
    }


    override fun onResultSingleChoice(key: Int, id: String?, name: String?) {
        when (key) {
            R.string.cap_nha -> {
                congtrinh?.cap_nha = name
            }
            R.string.ket_cau -> {
                congtrinh?.ket_cau_nha = name
                congtrinh?.mo_ta_cong_trinh =
                    danhMucCongTrinhSelected?.firstOrNull { it.prototype == name }?.describe
                calculateDonGiaXayDung()
            }
            R.string.so_sanh_voi_thuc_te -> {
                congtrinh?.so_sanh_thuc_te_co_so_ha_tang_ky_thuat = name
            }
            R.string.loai_cong_trinh -> {
                congtrinh?.loaiCongTrinh = name
                congtrinh?.ket_cau_nha = null
                congtrinh?.mo_ta_cong_trinh = null
                handleUILoaiCongTrinh()
                onTinhListKetCau()
                calculateDonGiaXayDung()
            }
        }
        congtrinh?.let {
            setDataUI(it)
        }
    }


    private fun showErrorInField(tf: CiticsTextField, msg: Int) {
        tf.endIconOnClickListener = {
            showBalloonPopup(
                getString(msg), tf
            )
        }
    }

    private fun calculateDonGiaXayDung() {
        if (level == LevelType.TAI_SAN_SO_SANH.type) {
            val loaiCongTrinh = binding.btnChonLoaiCongTrinhXd.getText().toStringOrNull()
            loaiCongTrinh?.let {
                val soTangNoi = binding.vSoTang?.getText().toStringOrNull()?.toIntOrNull()
                soTangNoi?.let {
                    val namHoanThanh =
                        binding.vNamXayDung?.getText()?.toStringOrNull()?.toIntOrNull()
                    namHoanThanh?.let {
                        val namSuaChua =
                            binding.vNamSuaChua?.getText()?.toStringOrNull()?.toIntOrNull()
                        val ketCau = binding.vKetCau?.getText()?.toStringOrNull()
                        ketCau?.let {
                            onCallApiCalculateGiaCongTrinh(
                                loaiCongTrinh,
                                soTangNoi,
                                namHoanThanh,
                                ketCau,
                                namSuaChua
                            )
                        }
                    }
                }
            }
        }
    }

    private fun onCallApiCalculateGiaCongTrinh(
        loaiCongTrinh: String,
        soTangNoi: Int,
        namHoanThanh: Int,
        ketCau: String,
        namSuaChua: Int?
    ) {
        viewModel.calculateDonGiaXd(
            EstimationConstructionRequest(
                loaiCongTrinh,
                soTangNoi,
                ketCau,
                namHoanThanh,
                namSuaChua
            )
        )
    }

    private fun handleUiByLevel() {
        var listViewGone = mutableListOf<View>()
        binding.run {
            when (level) {

                LevelType.TRA_CUU.type -> {
                    listViewGone = mutableListOf(
                        d1,
                        d2,
                        d3,
                        d4,
                        lnTangHam,
                        vCapNha,
                        vKetCau,
                        vMoTaCongTrinh,
                        lnCauTrucPhong,
                        lnTongDienTichThamDinh,
                        vCSKTHT, vSoSanhVoiThucTe, vGhiChu, lnTinhGiaCongTrinh
                    )
                }
                LevelType.LAM_TSSS_CHINH_SUA_GIA.type -> {
                    listViewGone = mutableListOf(
                        d1,
                        d2,
                        d3,
                        d4,
                        d9,
                        d10,
                        d11,
                        vCapNha,
                        vMoTaCongTrinh,
                        vCSKTHT,
                        vSoSanhVoiThucTe,
                        tfTongDienTich,
                        vGhiChu,
                        lnTinhGiaCongTrinh
                    )
                }
                LevelType.MUA_BAN.type -> {
                    listViewGone = mutableListOf(
                        d1,
                        d2,
                        d3,
                        d4,
                        lnTangHam,
                        vCapNha,
                        vKetCau,
                        vMoTaCongTrinh,
                        tfTongDienTich,
                        lnCauTrucPhong,
                        vCSKTHT, vSoSanhVoiThucTe, vGhiChu, lnTinhGiaCongTrinh
                    )
                }
                LevelType.THAM_DINH.type -> {
                    listViewGone = mutableListOf(lnTinhGiaCongTrinh, tfTongDienTich)
                }
                LevelType.TAI_SAN_SO_SANH.type -> {
                    listViewGone = mutableListOf(
                        lnCauTrucPhong, tfTongDienTich,
                        vCSKTHT, vSoSanhVoiThucTe, vGhiChu
                    )
                }
            }
        }
        listViewGone.forEach { it.visibility = View.GONE }
        binding.scroll.visibility = View.VISIBLE
    }

    private fun goToSingleChoiceScreen(
        title: Int,
        lstData: MutableList<ChooserItem>,
        selected: String?
    ) {
        goToSingleChoiceScreen(
            SingleChoiceData(
                title = title,
                lstData = lstData,
                selected = selected
            )
        )
    }

    private fun onAddingNewRoom(intent: Intent?) {
        val bundle: Bundle? = intent?.getBundleExtra(KEY_BUNDLE_DATA)
        val room =
            bundle?.getData<CongTrinh.ExtraRooms>(ThemPhongActivity.DATA_THEM_PHONG_MOI)

        congtrinh?.extra_rooms?.firstOrNull { it.title == room?.title }?.let {
            it.so_phong = room?.so_phong
        } ?: kotlin.run {
            room?.let {
                if (congtrinh?.extra_rooms == null) {
                    congtrinh?.extra_rooms = mutableListOf()
                }
                congtrinh?.extra_rooms?.add(it)
            }
        }
    }
}