package com.citics.valuation.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.citics.cbank.R
import com.citics.cbank.databinding.DialogLoadingBinding
import com.citics.valuation.extension.setDefaultWindowTheme

/**
 * Created by ChinhQT on 07/11/2022.
 */
open class LoadingDialog : DialogFragment() {
    private var _binding: DialogLoadingBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "LoadingDialog"

        fun getInstance(): LoadingDialog {
            return LoadingDialog()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.setDefaultWindowTheme()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.LoadingDialogTransparent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogLoadingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setCancelable(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    fun isShowing(): Boolean {
        return if (dialog != null) {
            dialog?.isShowing ?: false
        } else {
            dismiss()
            false
        }
    }
}
