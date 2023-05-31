package com.citics.valuation.ui.fragment.main.hoso

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.citics.cbank.databinding.FragmentMainHosoBinding
import com.citics.valuation.ui.activity.thongke.ThongKeActivity
import com.citics.valuation.ui.base.BaseFragment

class HoSoFragment : BaseFragment<FragmentMainHosoBinding, HoSoViewModel>(FragmentMainHosoBinding::inflate) {

    override val viewModel: HoSoViewModel by viewModels()
    override fun onClickListener() {
        super.onClickListener()
        binding.root.setOnClickListener {
            val itent = Intent(requireContext(), ThongKeActivity::class.java)
            startActivity(itent)
        }
    }
}