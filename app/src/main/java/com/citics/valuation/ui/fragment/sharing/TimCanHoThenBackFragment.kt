package com.citics.valuation.ui.fragment.sharing

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.citics.cagent.data.model.response.CanHoSuggestionResponse
import com.citics.cbank.R
import com.citics.valuation.data.model.others.ChooserItem
import com.citics.valuation.ui.fragment.tracuu.chitiettaisan.duan.TimCanHoFragment
import com.citics.valuation.utils.DialogUtils

class TimCanHoThenBackFragment : TimCanHoFragment() {

    companion object {
        const val KEY_UPDATE_MA_CAN_HO = "KEY_UPDATE_MA_CAN_HO"
        const val KEY_PROJECTID = "projectID"
        fun getArgument(projectId: String): Bundle {
            return bundleOf(
                KEY_PROJECTID to projectId
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.advancedSearchBtn.visibility = View.GONE
        binding.headerLayout.setTitle(getString(R.string.ma_can_ho))
    }

    override fun onClickCanHo(it: CanHoSuggestionResponse.ContentItem) {
        showDialogConfirmChangeMCH(it.text ?: "") {
//            setBackStackResult(
//                KEY_UPDATE_MA_CAN_HO,
//                ChooserItem(name = it.text ?: "", id = it.text ?: "", isCustomData = true)
//            )
//            findNavController().popBackStack()
        }
    }

    override fun onClickTitleAdvancedSearchBtn() {
//        setBackStackResult(
//            KEY_UPDATE_MA_CAN_HO,
//            ChooserItem(
//                name = binding.searchLayout.getTextInput()?.text?.toString() ?: "",
//                isCustomData = true,
//                id = ""
//            )
//        )
//        findNavController().popBackStack()
    }

    override fun onTextSearchChange(keyword: String) {
        super.onTextSearchChange(keyword)
        if (TextUtils.isEmpty(keyword)) {
            binding.advancedSearchBtn?.visibility = View.GONE
        } else {
            binding?.advancedSearchBtn?.visibility = View.VISIBLE
            binding?.advancedSearchBtn?.text = getString(
                R.string.tdg_add,
                "mã căn hộ " + binding?.searchLayout?.getTextInput()?.text?.toString()
            )
        }
    }

    private fun showDialogConfirmChangeMCH(mch: String, onSkip: () -> Unit) {
        DialogUtils.showDialogConfirm(
            requireContext(),
            childFragmentManager,
            R.string.xntdl,
            getString(R.string.des_xac_nhan_tai_du_lieu, mch),
            R.string.dong_y,
            R.string.kctldl,
            onOk = {
//                setBackStackResult(
//                    KEY_UPDATE_MA_CAN_HO,
//                    ChooserItem(name = mch, id = mch, isCustomData = false)
//                )
//                findNavController().popBackStack()
            },
            onNega = onSkip
        )
    }
}