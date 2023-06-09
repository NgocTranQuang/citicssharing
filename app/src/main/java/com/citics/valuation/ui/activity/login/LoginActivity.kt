package com.citics.valuation.ui.activity.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.citics.cbank.R
import com.citics.cbank.databinding.ActivityLoginBinding
import com.citics.valuation.ui.activity.main.StaticViewModel
import com.citics.valuation.ui.base.BaseActivity
import com.citics.valuation.ui.dialog.NormalDialog
import com.citics.valuation.ui.fragment.main.taikhoan.ProfileViewModel
import com.citics.valuation.utils.DialogType
import com.citics.valuation.utils.PHONE_SUPPORT_CITICS
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * Created by ChinhQT on 04/10/2022.
 */
@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels()
    private val staticViewModel: StaticViewModel by viewModels()

    override fun onConfigUI() {
        super.onConfigUI()
        val mesageError = intent?.getStringExtra(KEY_LOGIN_IN_NEW_DEVICE_MESSAGE)
        val title = intent?.getStringExtra(KEY_LOGIN_IN_NEW_DEVICE_TITLE)
        if (!TextUtils.isEmpty(mesageError)) {

            viewModel.logoutAccount()
            NormalDialog.Builder(this).setImage(R.drawable.ic_phone_pad, DialogType.ERROR)
                .setTitle(title ?: "")
                .setMessage(mesageError ?: "")
                .setPositiveButton(R.string.lien_he_citicsvn) { dialog, int ->
                    dialog.dismiss()
                    callPhone(PHONE_SUPPORT_CITICS)
                }.setNegativeButton(R.string.close) { dialog, int ->
                    dialog.dismiss()
                }.show(supportFragmentManager)
        }
        getDataStatic()
    }

    private fun getDataStatic() {
        staticViewModel.getAllStaticApp {
            Timber.d("Get Static thành cmn công")
        }
    }

    private fun callPhone(phoneSupportCitics: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${phoneSupportCitics}")
        startActivity(intent)
    }


    companion object {
        const val KEY_LOGIN_IN_NEW_DEVICE_MESSAGE = "KEY_LOGIN_IN_NEW_DEVICE_MESSAGE"
        const val KEY_LOGIN_IN_NEW_DEVICE_TITLE = "KEY_LOGIN_IN_NEW_DEVICE_TITLE"
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            return intent
        }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityLoginBinding
        get() = ActivityLoginBinding::inflate

}