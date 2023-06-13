package com.citics.valuation.ui.fragment.tracuu.chitiettaisan.duan

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.citics.cagent.data.model.response.CanHoSuggestionResponse
import com.citics.cbank.R
import com.citics.cbank.databinding.FragmentTimCanHoBinding
import com.citics.valuation.adapter.tracuu.MapFilterCanHoTimKiemAdapter
import com.citics.valuation.customview.HeaderLayout
import com.citics.valuation.extension.searchDebounce
import com.citics.valuation.ui.activity.tracuu.ChiTietCanHoActivity
import com.citics.valuation.ui.activity.tracuu.TimKiemCanHoViewModel
import com.citics.valuation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by ChinhQT on 10/11/2022.
 */
@AndroidEntryPoint
open class TimCanHoFragment :
    BaseFragment<FragmentTimCanHoBinding, TimCanHoVM>(FragmentTimCanHoBinding::inflate) {

    override val viewModel: TimCanHoVM by viewModels()
    private val activityViewModel: TimKiemCanHoViewModel by activityViewModels()

    private var mapFilterCanHoTimKiemAdapter: MapFilterCanHoTimKiemAdapter? = null
    private var projectID: String = ""
    private var keyword: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchLayout.getTextInput()
            .searchDebounce(lifecycleScope = lifecycle.coroutineScope) { s ->
                val keyword = s.toString()
                onTextSearchChange(keyword)
            }

        binding.advancedSearchBtn.setOnClickListener {
            onClickTitleAdvancedSearchBtn()
        }

        mapFilterCanHoTimKiemAdapter = MapFilterCanHoTimKiemAdapter {
            onClickCanHo(it)
        }
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.dataList.layoutManager = layoutManager
        binding.dataList.adapter = mapFilterCanHoTimKiemAdapter
    }

    override fun getHeaderLayout(): HeaderLayout? {
        return binding.headerLayout
    }

    override fun onObserverData() {
        super.onObserverData()
        dataListenerScope {
            viewModel.canHoSuggestionResponse.handleResponse(onLoading = {
                binding.statefulLayout.showLoading()
            }, onFail = {
                showErrorDialog(it?.title, it?.message)
                binding.statefulLayout.showEmpty()
            }) {
                val temporary = it?.data
                val size = temporary?.size ?: 0
                mapFilterCanHoTimKiemAdapter?.submitList(temporary)
                binding.statefulLayout.showContent()
            }
        }
        dataListenerScope {
            activityViewModel.projectID.collect {
                if (!TextUtils.isEmpty(it)) {
                    projectID = it
                }
            }
        }
    }

    open fun onTextSearchChange(keyword: String) {
        binding.statefulLayout.visibility = View.VISIBLE
        this.keyword = keyword
        binding.statefulLayout.showLoading()
        viewModel.suggestionSearch(projectID, keyword)
    }

    open fun onClickTitleAdvancedSearchBtn() {
        findNavController().navigateWithAnimation(R.id.timCanHoNangCao)
    }

    open fun onClickCanHo(it: CanHoSuggestionResponse.ContentItem) {
        startActivity(ChiTietCanHoActivity.newIntent(requireContext(), projectID, it.text ?: ""))
    }

}