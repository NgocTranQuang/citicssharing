package com.citics.valuation.ui.fragment.main.thongke

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.citics.valuation.ui.base.BaseFragment
import com.citics.cbank.databinding.FragmentMainThongKeBinding
import com.citics.valuation.ui.activity.thongke.ThongKeActivity

class ThongKeFragment :
    BaseFragment<FragmentMainThongKeBinding, ThongKeViewModel>(FragmentMainThongKeBinding::inflate) {

    override val viewModel: ThongKeViewModel by viewModels()
    override fun onClickListener() {
        super.onClickListener()
        binding.root.setOnClickListener {
            val itent = Intent(requireContext(), ThongKeActivity::class.java)
            startActivity(itent)
        }
    }
}