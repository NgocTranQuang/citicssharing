package com.citics.valuation.ui.fragment.main.hopthoai

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.citics.valuation.ui.base.BaseFragment
import com.citics.valuation.ui.fragment.hoso.HopThoaiViewModel
import com.citics.cbank.databinding.FragmentMainHopThoaiBinding

class HopThoaiFragment : BaseFragment<FragmentMainHopThoaiBinding, HopThoaiViewModel>(FragmentMainHopThoaiBinding::inflate) {

    override val viewModel: HopThoaiViewModel by viewModels()
}