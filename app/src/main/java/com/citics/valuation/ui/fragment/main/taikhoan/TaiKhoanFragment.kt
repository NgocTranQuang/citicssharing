package com.citics.valuation.ui.fragment.main.taikhoan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.citics.valuation.ui.base.BaseActivity
import com.citics.valuation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import com.citics.cbank.databinding.FragmentMainTaiKhoanBinding
import com.citics.valuation.ui.activity.taikhoan.TaiKhoanActivity
import com.citics.valuation.ui.activity.thongke.ThongKeActivity

@AndroidEntryPoint
class TaiKhoanFragment :
    BaseFragment<FragmentMainTaiKhoanBinding, ProfileViewModel>(FragmentMainTaiKhoanBinding::inflate) {
    override val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClickListener() {
        super.onClickListener()
        binding.root.setOnClickListener {
            val itent = Intent(requireContext(), TaiKhoanActivity::class.java)
            startActivity(itent)
        }
    }

    override fun onObserverData() {
        super.onObserverData()
        dataListenerScope {
            viewModel.userprofileResponse.handleResponse(onLoading = {
                viewModel.getUserProfile()
            }) {
                viewModel.saveInfo(it)
                it?.let {
                    showInfo("${it.data?.name}")
                }
            }
        }
    }

}