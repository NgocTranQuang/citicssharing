package com.citics.valuation.ui.fragment.main.tracuu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.citics.valuation.ui.base.BaseFragment
import com.citics.cbank.databinding.FragmentMainTraCuuBinding
import com.citics.valuation.ui.activity.thongke.ThongKeActivity
import com.citics.valuation.ui.activity.tracuu.TraCuuActivity

class TraCuuFragment :
    BaseFragment<FragmentMainTraCuuBinding, TraCuuViewModel>(FragmentMainTraCuuBinding::inflate) {
    override val viewModel: TraCuuViewModel by viewModels()
    override fun onClickListener() {
        super.onClickListener()
        binding.root.setOnClickListener {
            val itent = Intent(requireContext(), TraCuuActivity::class.java)
            startActivity(itent)
        }
    }

}