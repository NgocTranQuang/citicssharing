//package com.citics.valuation.ui.dialog
//
//import android.content.Context
//import android.content.DialogInterface
//import android.os.Bundle
//import android.view.View
//import androidx.fragment.app.FragmentManager
//import androidx.fragment.app.FragmentTransaction
//import com.citics.cbank.R
//import com.citics.cbank.databinding.LayoutUserLockDialogBinding
//import citics.sharing.extension.setDefaultWindowTheme
//import com.citics.valuation.utils.DialogType
//import com.citics.valuation.utils.LOCK_PERMANENT
//import com.citics.valuation.utils.LOCK_TEMP
//import timber.log.Timber
//
///**
// * Created by ChinhQT on 07/11/2022.
// */
//class LoginLockDialog :
//    BaseDialog<LayoutUserLockDialogBinding>(LayoutUserLockDialogBinding::inflate) {
//
//    var isType = DialogType.NORMAL
//    private var code: Int = 0
//    var title: CharSequence? = null
//    private var mMessage: CharSequence? = null
//
//    private var mPositive: CharSequence = ""
//    var mPositiveButtonListener: DialogInterface.OnClickListener? = null
//
//
//    companion object {
//        const val TAG = "TiepNhanHoSoDialog"
//    }
//
//    override fun onStart() {
//        super.onStart()
//        dialog?.setDefaultWindowTheme()
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        Timber.d("onViewCreated")
//
//        setDescription(mMessage.toString(), code)
//        binding?.descriptionTxt?.text = mMessage
//        var des = ""
//        if (code == LOCK_PERMANENT) {
//            des = getString(R.string.des_lock_tem)
//        } else {
//            des = getString(R.string.des_lock_permanent)
//        }
//        binding?.tvMoTa?.text = des
//        binding?.positiveBtn?.setOnClickListener {
//            dismiss()
//            mPositiveButtonListener?.onClick(this.dialog, DialogInterface.BUTTON_POSITIVE)
//        }
//
//    }
//
//    override fun getTheme(): Int = R.style.CiticsDialogTheme
//
//
//    private fun setDescription(msg: String, code: Int?) {
//        mMessage = msg
//        this.code = code ?: LOCK_TEMP
//    }
//
//
//    class Builder(val context: Context) {
//
//        var dialog = LoginLockDialog()
//
//        fun setMessage(text: String, code: Int?): Builder {
//            dialog.setDescription(text, code)
//            return this
//        }
//
//        fun show(manager: FragmentManager): LoginLockDialog {
//            dialog.show(manager, TAG)
//            return dialog
//        }
//
//        fun show(transaction: FragmentTransaction): LoginLockDialog {
//            dialog.show(transaction, TAG)
//            return dialog
//        }
//    }
//
//}