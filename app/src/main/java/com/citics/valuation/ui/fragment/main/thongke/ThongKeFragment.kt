package com.citics.valuation.ui.fragment.main.thongke

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

class ThongKeFragment : BaseFragment<FragmentMainThongKeBinding, ThongKeViewModel>(FragmentMainThongKeBinding::inflate) {
    //
//    private var _binding: FragmentMainThongKeBinding? = null
//
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!
//
//
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(ThongKeViewModel::class.java)
//
//        _binding = FragmentMainThongKeBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
//        return root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        view.setOnApplyWindowInsetsListener { v, insets ->
//            v.updatePadding(top = insets.systemWindowInsetTop)
//            insets
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
    override val viewModel: ThongKeViewModel by viewModels()
}