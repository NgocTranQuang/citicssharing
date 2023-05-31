package com.citics.valuation.ui.dialog

import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.citics.valuation.extension.setDefaultWindowTheme
import com.citics.valuation.extension.setHtml
import com.citics.valuation.utils.DialogType
import timber.log.Timber
import com.citics.cbank.R
import com.citics.cbank.databinding.LayoutDialogCiticsBinding

/**
 * Created by ChinhQT on 07/11/2022.
 */
class NormalDialog : BaseDialog<LayoutDialogCiticsBinding>(LayoutDialogCiticsBinding::inflate) {

    var isType = DialogType.NORMAL
    private var iconResource: Int = 0
    var title: CharSequence? = null
    private var mMessage: CharSequence? = null

    private var mPositive: CharSequence = ""
    var mPositiveButtonListener: DialogInterface.OnClickListener? = null
    private var mNegative: CharSequence = ""
    var mNegativeButtonListener: DialogInterface.OnClickListener? = null

    var gravity: Int = Gravity.CENTER

    companion object {
        const val TAG = "NormalDialog"
    }

    override fun onStart() {
        super.onStart()
        dialog?.setDefaultWindowTheme()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("onViewCreated")

        setImage(iconResource, isType)
        setTitle(title.toString())
        setDescription(mMessage.toString())
        setPositiveButton(mPositive.toString())
        setNegativeButton(mNegative.toString())
        setStart(gravity)

        isCancelable = false

        binding?.positiveBtn?.setOnClickListener {
            mPositiveButtonListener?.onClick(this.dialog, DialogInterface.BUTTON_POSITIVE)
            dismiss()
        }

        binding?.negativeBtn?.setOnClickListener {
            mNegativeButtonListener?.onClick(this.dialog, DialogInterface.BUTTON_NEGATIVE)
            dismiss()
        }
    }

//    override fun getTheme(): Int = R.style.CiticsDialogTheme

    private fun setTitle(msg: String) {
        title = msg
        binding?.titleTxt?.text = title
    }

    private fun setImage(resourceID: Int, isType: DialogType = DialogType.NORMAL) {
        this.iconResource = resourceID
        this.isType = isType

        binding?.iconImg?.setImageResource(resourceID)
        when (isType) {
            DialogType.NORMAL -> {
                binding?.iconImg?.imageTintList = ColorStateList.valueOf(
                    ResourcesCompat.getColor(
                        resources, R.color.icon_normal, binding?.iconImg?.context?.theme
                    )
                )
            }
            DialogType.CONFIRM -> {
                binding?.iconImg?.imageTintList = ColorStateList.valueOf(
                    ResourcesCompat.getColor(
                        resources, R.color.color_red, binding?.iconImg?.context?.theme
                    )
                )
            }
            DialogType.ERROR -> {
                binding?.iconImg?.imageTintList = ColorStateList.valueOf(
                    ResourcesCompat.getColor(
                        resources, R.color.icon_error, binding?.iconImg?.context?.theme
                    )
                )
            }
            else -> {}
        }
    }

    private fun setStart(gravity: Int) {
        this.gravity = gravity
        binding?.descriptionTxt?.gravity = gravity
    }

    private fun setDescription(msg: String) {
        mMessage = msg
        if (msg.contains(">")) {
            binding?.descriptionTxt?.setHtml(msg)
        } else {
            binding?.descriptionTxt?.text = msg
        }
    }

    private fun setPositiveButton(msg: String) {
        mPositive = msg
        if (msg.isEmpty()) {
            binding?.positiveBtn?.visibility = View.GONE
        } else {
            binding?.positiveBtn?.text = msg
            binding?.positiveBtn?.visibility = View.VISIBLE
        }
    }

    private fun setNegativeButton(msg: String) {
        mNegative = msg
        if (msg.isEmpty()) {
            binding?.negativeBtn?.visibility = View.GONE
        } else {
            binding?.negativeBtn?.text = msg
            binding?.negativeBtn?.visibility = View.VISIBLE
        }
    }

    class Builder(val context: Context) {

        var dialog = NormalDialog()

        fun setTitle(text: CharSequence): Builder {
            dialog.setTitle(text.toString())
            return this
        }

        fun setTitle(@StringRes textId: Int): Builder {
            dialog.setTitle(context.getString(textId))
            return this
        }

        fun setMessage(text: CharSequence): Builder {
            dialog.setDescription(text.toString())
            return this
        }

        fun setMessage(text: String): Builder {
            dialog.setDescription(text)
            return this
        }

        fun setMessage(@StringRes textId: Int): Builder {
            dialog.setDescription(context.getString(textId))
            return this
        }

        fun setImage(
            resourceID: Int, isType: DialogType = DialogType.NORMAL
        ): Builder {
            dialog.setImage(resourceID, isType)
            return this
        }

        fun setPositiveButton(
            @StringRes textId: Int, listener: (DialogInterface, Int) -> Unit
        ): Builder {
            dialog.setPositiveButton(context.getString(textId))
            dialog.mPositiveButtonListener = DialogInterface.OnClickListener(listener)
            return this
        }

        fun setNegativeButton(
            @StringRes textId: Int, listener: (DialogInterface, Int) -> Unit
        ): Builder {
            dialog.setNegativeButton(context.getString(textId))
            dialog.mNegativeButtonListener = DialogInterface.OnClickListener(listener)
            return this
        }

        fun setStart(): Builder {
            dialog.setStart(Gravity.START)
            return this
        }

        fun show(manager: FragmentManager): NormalDialog {
            dialog.show(manager, TAG)
            return dialog
        }

        fun show(transaction: FragmentTransaction): NormalDialog {
            dialog.show(transaction, TAG)
            return dialog
        }
    }

}