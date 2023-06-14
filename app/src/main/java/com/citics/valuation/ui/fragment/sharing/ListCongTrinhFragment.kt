package com.citics.valuation.ui.fragment.sharing

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import citics.sharing.customview.HeaderLayout
import citics.sharing.data.model.response.CongTrinh
import com.citics.cbank.R
import com.citics.cbank.databinding.FragmentDanhSachCongTrinhBinding
import com.citics.valuation.adapter.list_cong_trinh.DanhSachCongTrinhAdapter
import citics.sharing.extension.toJson
import com.citics.valuation.ui.activity.chitietcongtrinh.ChiTietCongTrinhActivity
import com.citics.valuation.ui.activity.chitietcongtrinh.ChiTietCongTrinhViewModel
import com.citics.valuation.ui.base.BaseActivity
import com.citics.valuation.ui.base.BaseFragment
import com.citics.valuation.ui.base.BaseViewModel
import timber.log.Timber

class ListCongTrinhFragment :
    BaseFragment<FragmentDanhSachCongTrinhBinding, BaseViewModel>(FragmentDanhSachCongTrinhBinding::inflate) {

    private var adapter: DanhSachCongTrinhAdapter? = null
    private var listCongTrinh: MutableList<CongTrinh>? = null
    private val activityViewModel: ChiTietCongTrinhViewModel by activityViewModels()


    override val viewModel: BaseViewModel by viewModels()

    companion object {
        const val REQUEST_KEY_LIST_CONG_TRINH = "REQUEST_KEY_LIST_CONG_TRINH"
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        listCongTrinh = args.listCongTrinh?.toMutableList() ?: mutableListOf()
//        if (listCongTrinh?.size == 0) {
//            findNavController().navigate(
//                ListCongTrinhFragmentDirections.actionNhaDatDetailFragmentToBoSungCongTrinh(
//                    ""
//                )
//            )
//        }
//    }

    override fun onObserverData() {
        super.onObserverData()
        dataListenerScope {
            activityViewModel.asset.collect {
                listCongTrinh = it?.properties?.cong_trinh
                adapter?.mainList = listCongTrinh
            }
        }
    }


//    private fun onResultSingleChoice() {
//        setFragmentResultListener(REQUEST_KEY_CONG_TRINH) { _, bundle ->
//            val data = bundle.getString(REQUEST_KEY_CONG_TRINH)
//            data?.let {
//                val dataObject = it.toObject<CongTrinh>()
//                dataObject?.let { congtrinh ->
//                    val index = listCongTrinh?.indexOfFirst { it.id == congtrinh.id }
//                    if (index == -1) {
//                        listCongTrinh?.add(congtrinh)
//                    } else {
//                        index?.let {
//                            listCongTrinh?.set(it, congtrinh)
//                        }
//                    }
//                }
//            }
//        }
//    }

    override fun getHeaderLayout(): HeaderLayout? {
        return binding.headerLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DanhSachCongTrinhAdapter(
            requireContext(), listCongTrinh ?: mutableListOf(), mutableListOf()
        ) { title, item ->
            goToEditCongTrinhScreen(title, item)
        }
        binding.rvListCongTrinh.adapter = adapter
    }

    private fun goToEditCongTrinhScreen(title: String, it: CongTrinh) {
        activityViewModel.setCongTrinh(it)
        findNavController().navigate(R.id.bosungCongTrinh)
    }



    override fun onClickListener() {
        super.onClickListener()
        binding.lnThemCongTrinh.setOnClickListener {
            activityViewModel.setCongTrinh(CongTrinh())
            findNavController().navigate(R.id.bosungCongTrinh)
        }
    }

    override fun onBackPress() {
        (activity as BaseActivity<*, *>).setDataResult(
            bundleOf(
                ChiTietCongTrinhActivity.KEY_ASSET_DETAIL to activityViewModel.asset.value,
                ChiTietCongTrinhActivity.KEY_ASSET_EXTRA to activityViewModel.extraData.value?.toJson()
            )
        )
        super.onBackPress()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("Vô Hàm onDestroyView")
    }

    override fun onDestroy() {
        adapter?.mainList?.forEach {
            if (it.id?.startsWith("temp_") == true) {
                it.id = null
            }
        }
//        val bundle = bundleOf(
//            REQUEST_KEY_LIST_CONG_TRINH to adapter?.mainList?.toJson()
//        )
//        setFragmentResult(REQUEST_KEY_LIST_CONG_TRINH, bundle)
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("Vô Hàm onDetach")

    }

}