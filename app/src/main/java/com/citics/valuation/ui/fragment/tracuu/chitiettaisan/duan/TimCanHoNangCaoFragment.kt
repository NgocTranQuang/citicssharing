package com.citics.valuation.ui.fragment.tracuu.chitiettaisan.duan

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.citics.cagent.data.model.response.CanHoSuggestionResponse
import com.citics.cagent.data.model.response.OptionsSuggestionResponse
import com.citics.cbank.R
import com.citics.cbank.databinding.FragmentTimCanHoBinding
import com.citics.cbank.databinding.FragmentTimCanHoNangCaoBinding
import com.citics.valuation.adapter.tracuu.MapFilterCanHoTimKiemAdapter
import com.citics.valuation.customview.HeaderLayout
import com.citics.valuation.data.model.others.ChooserItem
import com.citics.valuation.data.model.others.SelectorItem
import com.citics.valuation.data.model.others.SingleChoiceData
import com.citics.valuation.extension.getData
import com.citics.valuation.extension.searchDebounce
import com.citics.valuation.extension.toListChooser
import com.citics.valuation.ui.activity.choice.SingleChoiceAndAddingDataActivity
import com.citics.valuation.ui.activity.tracuu.ChiTietCanHoActivity
import com.citics.valuation.ui.activity.tracuu.TimKiemCanHoViewModel
import com.citics.valuation.ui.base.BaseChooserFragment
import com.citics.valuation.ui.base.BaseFragment
import com.citics.valuation.utils.KEY_BUNDLE_DATA
import com.citics.valuation.utils.StaticDataUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import retrofit2.http.Query

/**
 * Created by ChinhQT on 10/11/2022.
 */
@AndroidEntryPoint
open class TimCanHoNangCaoFragment :
    BaseChooserFragment<FragmentTimCanHoNangCaoBinding, TimCanHoVM>(FragmentTimCanHoNangCaoBinding::inflate) {

    private var listStaticFilter: List<OptionsSuggestionResponse.Data>? = null
    override val viewModel: TimCanHoVM by viewModels()
    private val activityViewModel: TimKiemCanHoViewModel by activityViewModels()

    private var projectID: String = ""


    override fun onObserverData() {
        super.onObserverData()

        dataListenerScope {
            activityViewModel.projectID.collect {
                projectID = it
            }
        }
        dataListenerScope {
            viewModel.optionsSuggestionResponse.handleResponse(onLoading = {
                projectID.let {
                    if (it.isNotEmpty()) {
                        viewModel.getOptionsSuggestion(it)
                    }
                }
            }) {
                listStaticFilter = it?.data
            }
        }
    }

    override fun onClickListener() {
        super.onClickListener()

        binding.thapChooser.onClickListener = {
            goToChooseScreen((listStaticFilter?.map { it.name }
                ?: mutableListOf<String>()).toListChooser(),
                SingleChoiceAndAddingDataActivity.THAP_TYPE, ChooserItem(
                    binding.thapChooser.getText().toString(),
                    binding.thapChooser.getText().toString()
                ))
        }

        binding.tangChooser.onClickListener = {
            val listTang = (listStaticFilter?.firstOrNull {
                it.name == binding.thapChooser.getText().toString()
            }?.items?.map { it.name } ?: mutableListOf()).toListChooser()

            goToChooseScreen(
                listTang, SingleChoiceAndAddingDataActivity.TANG_TYPE, ChooserItem(
                    binding.tangChooser.getText().toString(),
                    binding.tangChooser.getText().toString()
                )
            )
        }
        binding.loaiCanHoChooser.setOnClickListener {
            goToSingleChoiceScreen(
                SingleChoiceData(
                    R.string.loai_can_ho,
                    StaticDataUtils.staticCanHo?.loaiCanHo?.toMutableList()?.toListChooser(),
                    binding.loaiCanHoChooser.getText()?.toString()
                )
            )
        }

        binding.btnUpdate.setOnClickListener {
            activityViewModel.setMapFilterNangCao(getMap())
            findNavController().navigateWithAnimation(R.id.ketQuaTimKiem)
        }
    }

    private fun getMap(): HashMap<String, Any?> {
        return hashMapOf(
            "project_id" to projectID,
            "thap" to binding.thapChooser.getText().toString(),
            "tang" to binding.tangChooser.getText().toString(),
            "dien_tich_thong_thuy" to binding.dienTichThongThuyTF.getText().toString(),
            "dien_tich_tim_tuong" to binding.vDienTichTimTuong.getText().toString()
        )
    }

    override fun onResultSingleChoice(title: Int, id: String?, name: String?) {
        when (title) {
            R.string.loai_can_ho -> {
                binding.loaiCanHoChooser.setText(name ?: "")
            }
        }
    }

    override fun setDataSingleChoiceAndAddingCallBack(intent: Intent?) {
        val bundle = intent?.getBundleExtra(KEY_BUNDLE_DATA)
        val itemSelected =
            bundle?.getData<ChooserItem>(SingleChoiceAndAddingDataActivity.LCH_DATA_KEY)
        val typeChooser = bundle?.getInt(SingleChoiceAndAddingDataActivity.LCH_TYPE_KEY)
        when (typeChooser) {
            SingleChoiceAndAddingDataActivity.THAP_TYPE -> {
                onResultThap(itemSelected?.name)
            }
            SingleChoiceAndAddingDataActivity.TANG_TYPE -> {
                onResultTang(itemSelected?.name)
            }
        }
    }

    private fun onResultTang(name: String?) {
        binding.tangChooser.setText(name ?: "")
    }

    private fun onResultThap(name: String?) {
        binding.thapChooser.setText(name ?: "")
        binding.tangChooser.setText("")
    }

}