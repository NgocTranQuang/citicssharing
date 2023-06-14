package com.citics.valuation.ui.dialog

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import com.citics.cbank.databinding.DialogConfirmUpdateCvalueBinding
import citics.sharing.extension.setDefaultWindowTheme

class ConfirmUpdateCValueDialog(giaHienTai: String, ngayCapNhatCuoi: String) : BaseDialog<DialogConfirmUpdateCvalueBinding>
    (DialogConfirmUpdateCvalueBinding::inflate) {
    var mPositiveButtonListener: DialogInterface.OnClickListener? = null
    var giaHienTai: String? = ""
    var ngayCapNhatCuoi: String? = ""

    init {
        this.giaHienTai = giaHienTai
        this.ngayCapNhatCuoi = ngayCapNhatCuoi
    }

    override fun onStart() {
        super.onStart()
        dialog?.setDefaultWindowTheme()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            vGiaHienTai.setText(giaHienTai.toString())
            vNgayCapNhatCuoi.setText(ngayCapNhatCuoi)
            vPositiveButton.setOnClickListener {
                mPositiveButtonListener?.onClick(dialog, DialogInterface.BUTTON_POSITIVE)
                dismiss()
            }
            vNegativeButton.setOnClickListener {
                dismiss()
            }
        }
    }


    @SuppressLint("ResourceType")
    fun setPositiveButton(
        @StringRes textId: Int, listener: (DialogInterface, Int) -> Unit
    ){
        this.mPositiveButtonListener = DialogInterface.OnClickListener(listener)
        if(textId > 0){
            binding?.vPositiveButton?.setText(textId)
        }
    }
}