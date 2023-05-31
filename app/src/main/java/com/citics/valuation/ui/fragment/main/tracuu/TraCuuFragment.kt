package com.citics.valuation.ui.fragment.main.tracuu

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

class TraCuuFragment : BaseFragment<FragmentMainTraCuuBinding, TraCuuViewModel>() {
    override val viewModel: TraCuuViewModel by viewModels()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainTraCuuBinding
        get() = FragmentMainTraCuuBinding::inflate


}