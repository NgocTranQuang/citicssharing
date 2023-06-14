package com.citics.valuation.ui.fragment.tracuu.chitiettaisan.duan

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import citics.sharing.customview.HeaderLayout
import com.citics.cagent.data.model.response.tham_dinh.Properties
import com.citics.cbank.databinding.FragmentEditCanHoTracuuBinding
import citics.sharing.extension.copyByJson
import com.citics.valuation.ui.activity.tracuu.ChiTietTaiSanTraCuuViewModel
import com.citics.valuation.ui.base.BaseChooserFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChinhSuaCanHoTraCuuFragment :
    BaseChooserFragment<FragmentEditCanHoTracuuBinding, EditCanHoViewModel>(
        FragmentEditCanHoTracuuBinding::inflate
    ) {
    private var properties: Properties? = null
    private var projectID: String? = null

    private val activityViewModel: ChiTietTaiSanTraCuuViewModel by activityViewModels()
    override val viewModel: EditCanHoViewModel by viewModels()

//    private var listStaticFilter: List<OptionsSuggestionResponse.Data>? = null

    // action
    private var actionSingleChoice: String? = null


    companion object {
        const val REQUEST_KEY_LOAI_CAN_HO = "REQUEST_KEY_LOAI_CAN_HO"
        const val REQUEST_KEY_HUONG_NHA = "REQUEST_KEY_HUONG_NHA"
        const val REQUEST_KEY_VI_TRI = "REQUEST_KEY_VI_TRI"
        const val REQUEST_KEY_BAN_CONG = "REQUEST_KEY_BAN_CONG"
        const val REQUEST_KEY_LO_GIA = "REQUEST_KEY_LO_GIA"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lnChiTietCanHo.setLauncher(startSingleChoiceForResult, startMultiChoiceForResult)
        binding.lnChiTietCanHo.startSingleChoiceAndAddingForResult =
            startSingleChoiceAndAddingForResult
    }

    override fun onObserverData() {
        super.onObserverData()
        dataListenerScope {
            activityViewModel.assetDetail.handleResponse {
                properties = it?.data?.properties?.copyByJson()
                projectID = it?.data?.properties?.ma_du_an
                binding.lnChiTietCanHo.setData(properties, it?.data?.level)
            }
        }
        dataListenerScope {
            viewModel.optionsSuggestionResponse.handleResponse(onLoading = {
                projectID?.let {
                    if (it.isNotEmpty()) {
                        viewModel.getOptionsSuggestion(it)
                    }
                }
            }) {
//                listStaticFilter = it?.data
                binding.lnChiTietCanHo.setListCanHoByDuAn(it?.data)
            }
        }
        dataListenerScope {
            viewModel.chDetailResponse.handleResponse {
                hideLoading()
                it?.let {
                    activityViewModel.updateAssetDetail(it)
                }
                onBackHeader()
            }
        }
    }

//    override fun registerObserver() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                mainShareVM.staticCanHo.collect { dataResource ->
//                    when (dataResource) {
//                        is Resource.Loading -> {
//                            mainShareVM.getStaticCanHo()
//                        }
//                        is Resource.Success -> {
//                            staticCanHo = dataResource.data
//                        }
//                        else -> {
//                            activity?.let {
//                                showErrorDialog(dataResource.error)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        dataListenerScope {
//            editCanHoViewModel.optionsSuggestionResponse.handleResponse(onLoading = {
//                projectID?.let {
//                    if (it.isNotEmpty()) {
//                        editCanHoViewModel.getOptionsSuggestion(it)
//                    }
//                }
//            }) {
//                listStaticFilter = it?.data
//            }
//        }
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                editCanHoViewModel.chDetailResponse.collect { dataResource ->
//                    when (dataResource) {
//                        is Resource.Loading -> {
//
//                        }
//                        is Resource.Success -> {
//                            makeLoading(false)
//                            mainShareVM.updateNhaDatDetail(dataResource.data?.extra)
//                            findNavController().popBackStack()
//                        }
//                        else -> {
//                            makeLoading(false)
//                            showErrorDialog(dataResource.error)
//                        }
//                    }
//                }
//            }
//        }
//        onResultSingleChoice()
//    }


    override fun getHeaderLayout(): HeaderLayout {
        return binding.headerLayout
    }

    override fun onClickListener() {
        super.onClickListener()
        binding.run {
//            vloaiCanHo.onClickListener = {
//                actionSingleChoice = REQUEST_KEY_LOAI_CAN_HO
//                goToSingleChoiceScreen(
//                    R.string.loai_can_ho,
//                    staticCanHo?.loaiCanHo?.toMutableList().toListChooser(),
//                    vloaiCanHo.getText().toString()
//                )
//            }
//            vThap.onClickListener = {
//                goToChooseScreen((listStaticFilter?.map { it.name }
//                    ?: mutableListOf<String>()).toListChooser().toTypedArray(),
//                    THAP_TYPE,
//                    ChooserItem(
//                        binding?.vThap?.getText().toString(), binding?.vThap?.getText().toString()
//                    ))
//
//            }
//
//            binding?.vBanCong?.setOnClickListener {
//                actionSingleChoice = REQUEST_KEY_BAN_CONG
//                goToSingleChoiceScreen(
//                    R.string.ban_cong,
//                    staticCanHo?.banCong?.toListChooser() ?: mutableListOf(),
//                    binding?.vBanCong?.getText()?.toString()
//                )
//            }
//
//            binding?.vLoGia?.setOnClickListener {
//                actionSingleChoice = REQUEST_KEY_LO_GIA
//                goToSingleChoiceScreen(
//                    R.string.lo_gia,
//                    staticCanHo?.loGia?.toListChooser() ?: mutableListOf(),
//                    binding?.vLoGia?.getText()?.toString()
//                )
//            }
//
//            vTang.onClickListener = {
//                val listTang = (listStaticFilter?.firstOrNull {
//                    it.name == vThap.getText().toString()
//                }?.items?.map { it.name } ?: mutableListOf()).toListChooser().toTypedArray()
//
//                goToChooseScreen(
//                    listTang, TANG_TYPE, ChooserItem(
//                        binding?.vTang?.getText().toString(), binding?.vTang?.getText().toString()
//                    )
//                )
//            }
//            vViTri.onClickListener = {
//                actionSingleChoice = REQUEST_KEY_VI_TRI
//                goToSingleChoiceScreen(
//                    R.string.vi_tri_can_ho,
//                    staticCanHo?.viTriCanHo.toListChooser(),
//                    vViTri.getText().toString()
//                )
//            }
//            vHuongNha.onClickListener = {
//                actionSingleChoice = REQUEST_KEY_HUONG_NHA
//                val listHuongNha = ((listStaticFilter?.firstOrNull {
//                    it.name == vThap.getText().toString()
//                }?.items?.firstOrNull {
//                    it.name == vTang.getText().toString()
//                }?.items?.map { it.name }) ?: mutableListOf()).toListChooser().toTypedArray()
//                goToSingleChoiceScreen(
//                    R.string.huong_nha,
//                    LIST_HUONG_NHA, binding?.vHuongNha?.getText().toString()
//                )
//            }
//
            btnUpdate.onClickListener {
                showLoading()
                properties?.let {
                    viewModel.updateCanHo(
                        properties?.id ?: "", properties?.loai_tai_san ?: "", it
                    )
                }
            }
        }
    }

//    private fun setDataToUploadServer() {
//        binding?.run {
//            properties?.dien_tich_thong_thuy =
//                vDienTichThongThy.getText().toString().toFloatOrNull()
//            properties?.dien_tich_tim_tuong = vDienTichTimTuong.getText().toString().toFloatOrNull()
//            properties?.so_phong_ngu = vPhongNgu.getText().toString().toIntOrNull()
//            properties?.so_wc = vPhongVeSinh.getText().toString().toIntOrNull()
//            properties?.so_phong_khach = vPhongKhach.getText().toString()
//            properties?.so_phong_bep = vPhongBep.getText().toString()
//            properties?.canh_quan = vHuongCanhQuan.getText().toString()
//            properties?.vi_tri_can_goc = vViTri.getText().toString()
//        }
//    }

//    private fun goToChooseScreen(
//        toTypedArray: Array<ChooserItem>, thapType: Int, chooserItem: ChooserItem
//    ) {
//
//        findNavController().navigate(
//            R.id.ChooserFragment,
//            ChooserFragment.getArgument(toTypedArray.toList(), chooserItem, thapType)
//        )
//    }

//    private fun binData() {
//        binding?.run {
//            vloaiCanHo.setText(properties?.loai_can_ho ?: "")
//            vThap.setText(properties?.thap ?: "")
//            vTang.setText(properties?.tang ?: "")
//            vDienTichThongThy.setText(properties?.dien_tich_thong_thuy?.toString()) {
//                properties?.dien_tich_thong_thuy = it?.toFloatOrNull()
//            }
//            vDienTichTimTuong.setText(properties?.dien_tich_tim_tuong?.toString() ?: "") {
//                properties?.dien_tich_tim_tuong = it?.toFloatOrNull()
//            }
//            vPhongNgu.setText(properties?.so_phong_ngu?.toString() ?: "") {
//                properties?.so_phong_ngu = it?.toIntOrNull()
//            }
//            vPhongVeSinh.setText(properties?.so_wc?.toString() ?: "") {
//                properties?.so_wc = it?.toIntOrNull()
//            }
//            vPhongKhach.setText(properties?.so_phong_khach ?: "") {
//                properties?.so_phong_khach = it
//            }
//            vPhongBep.setText(properties?.so_phong_bep ?: "") {
//                properties?.so_phong_bep = it
//            }
//            vHuongNha.setText(properties?.huong ?: "")
//            vHuongCanhQuan.setText(properties?.canh_quan ?: "")
//            vViTri.setText(properties?.vi_tri_can_goc ?: "")
//            vBanCong.setText(properties?.ban_cong ?: "")
//            vLoGia.setText(properties?.lo_gia ?: "")
//        }
//    }
//
//
//    private fun goToSingleChoiceScreen(
//        title: Int, lstData: MutableList<ChooserItem>, selected: String?
//    ) {
//        findNavController().navigate(
//            R.id.singleChoiceFragment,
//            SingleChoiceFragment.getArgumentFragment(title, lstData, selected)
//        )
//    }
//
//    private fun onResultSingleChoice() {
//        setFragmentResultListener(SingleChoiceFragment.REQUEST_KEY_SINGLE_CHOICE) { _, bundle ->
//            val name =
//                bundle.getString(SingleChoiceFragment.REQUEST_KEY_SINGLE_CHOICE_SELECTED_NAME)
//            when (actionSingleChoice) {
//                REQUEST_KEY_LOAI_CAN_HO -> {
//                    onResultLoaiCanHo(name)
//                }
//                REQUEST_KEY_HUONG_NHA -> {
//                    onResultHuongNha(name)
//                }
//                REQUEST_KEY_VI_TRI -> {
//                    onResultViTri(name)
//                }
//                REQUEST_KEY_BAN_CONG -> {
//                    onResultBanCong(name)
//                }
//                REQUEST_KEY_LO_GIA -> {
//                    onResultLoGia(name)
//                }
//            }
//        }
//
//        setFragmentResultListener(ChooserFragment.REQUEST_KEY) { _, bundle ->
//            when (bundle.getInt(ChooserFragment.LCH_TYPE_KEY)) {
//                THAP_TYPE -> {
//                    val thapChooser = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                        bundle.getParcelable(ChooserFragment.LCH_DATA_KEY, ChooserItem::class.java)
//                    } else {
//                        bundle.getParcelable(ChooserFragment.LCH_DATA_KEY)
//                    }
//                    onResultThap(thapChooser?.name)
//                }
//                TANG_TYPE -> {
//                    val tangChooser = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                        bundle.getParcelable(ChooserFragment.LCH_DATA_KEY, ChooserItem::class.java)
//                    } else {
//                        bundle.getParcelable(ChooserFragment.LCH_DATA_KEY)
//                    }
//                    onResultTang(tangChooser?.name)
//                }
//
//                HUONG_TYPE -> {
//                    val huongChooser = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                        bundle.getParcelable(ChooserFragment.LCH_DATA_KEY, ChooserItem::class.java)
//                    } else {
//                        bundle.getParcelable(ChooserFragment.LCH_DATA_KEY)
//                    }
//                    onResultHuongNha(huongChooser?.name)
//                }
//            }
//        }
//    }
//
//    private fun onResultLoGia(name: String?) {
//        properties?.lo_gia = name
//        binding?.vLoGia?.setText(name ?: "")
//    }
//
//    private fun onResultBanCong(name: String?) {
//        properties?.ban_cong = name
//        binding?.vBanCong?.setText(name ?: "")
//    }
//
//    private fun onResultViTri(name: String?) {
//        properties?.vi_tri_can_goc = name
//        binding?.vViTri?.setText(name ?: "")
//    }
//
//    private fun onResultHuongNha(name: String?) {
//        properties?.huong = name
//        binding?.vHuongNha?.setText(name ?: "")
//    }
//
//    private fun onResultTang(name: String?) {
//        if (properties?.tang == name) {
//            return
//        }
//        properties?.tang = name
//        binding?.vTang?.setText(name ?: "")
//
//        properties?.huong = ""
//        binding?.vHuongNha?.setText("")
//    }
//
//    private fun onResultThap(name: String?) {
//        if (properties?.thap == name) {
//            return
//        }
//        properties?.thap = name
//        binding?.vThap?.setText(name ?: "")
//
//        properties?.tang = ""
//        binding?.vTang?.setText("")
//
//        properties?.huong = ""
//        binding?.vHuongNha?.setText("")
//    }
//
//    private fun onResultLoaiCanHo(name: String?) {
//        properties?.loai_can_ho = name
//        binding?.vloaiCanHo?.setText(name ?: "")
//    }

    override fun setDataSingleChoiceAndAddingCallBack(intent: Intent?) {
        binding.lnChiTietCanHo.setDataSingleChoiceAndAddingCallBack(intent)
    }

    override fun setDataMultiChoiceCallBack(intent: Intent?) {
        binding.lnChiTietCanHo.setDataMultiChoiceCallBack(intent)
    }

    override fun setDataSingleChoiceCallBack(intent: Intent?) {
        binding.lnChiTietCanHo.setDataSingleChoiceCallBack(intent)
    }
}