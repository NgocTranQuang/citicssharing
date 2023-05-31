package com.citics.valuation.ui.fragment.main.hopthoai

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.citics.valuation.ui.base.BaseFragment
import com.citics.valuation.ui.fragment.hoso.HopThoaiViewModel
import com.citics.cbank.databinding.FragmentMainHopThoaiBinding
import com.citics.valuation.ui.activity.hopthoai.HopThoaiActivity
import com.citics.valuation.ui.activity.thongke.ThongKeActivity

class HopThoaiFragment :
    BaseFragment<FragmentMainHopThoaiBinding, HopThoaiViewModel>(FragmentMainHopThoaiBinding::inflate) {

    override val viewModel: HopThoaiViewModel by viewModels()
    override fun onClickListener() {
        super.onClickListener()
        binding.root.setOnClickListener {
            val itent = Intent(requireContext(), HopThoaiActivity::class.java)
            startActivity(itent)
        }
    }
}