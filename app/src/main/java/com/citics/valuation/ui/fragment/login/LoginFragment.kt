package com.citics.valuation.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import com.citics.cagent.data.model.request.NotificationRequest
import com.citics.cagent.data.model.response.UserResponse
import com.citics.cbank.BuildConfig
import com.citics.cbank.R
import com.citics.cbank.databinding.FragmentLoginBinding
import citics.sharing.extension.emailValidator
import citics.sharing.extension.phoneValidator
import com.citics.valuation.ui.activity.main.MainActivity
import com.citics.valuation.ui.base.BaseBiometricFragment
import com.citics.valuation.ui.dialog.NormalDialog
import com.citics.valuation.utils.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


/**
 * Created by ChinhQT on 25/09/2022.
 */
@AndroidEntryPoint
class LoginFragment :
    BaseBiometricFragment<FragmentLoginBinding, LoginViewModel>(FragmentLoginBinding::inflate) {

    override val viewModel: LoginViewModel by viewModels()

    private var emailBalloon: String = ""
    private var passBalloon: String = ""

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    //region Life Cycle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.emailTF.setText(BuildConfig.default_login)
        binding.passTF.setText(BuildConfig.default_password)
        super.onViewCreated(view, savedInstanceState)
        handleUiUsername()
    }

    override fun onClickListener() {
        super.onClickListener()
        binding.registerTxt.setOnClickListener {

        }

        binding.loginBtn.setOnClickListener {
            if (binding.emailTF.getText().toString().emailValidator()) {
                disableButtonLogin()
                viewModel.loginAccount(
                    binding.emailTF.getText().toString(),
                    binding.passTF.getText().toString(),
                    true
                )
            } else {
                if (binding.emailTF.getText().toString().phoneValidator()) {
                    disableButtonLogin()
                    viewModel.loginAccount(
                        binding.emailTF.getText().toString(),
                        binding.passTF.getText().toString(),
                        true
                    )
                } else {
                    emailBalloon = getString(R.string.login_wrong)
                    binding.emailTF.setState(true)
                }
            }
        }

        binding.forgetPassBtn.setOnClickListener {

        }

        binding.emailTF.endIconOnClickListener = {
            showBalloonPopup(emailBalloon, it)
        }

        binding.passTF.endIconOnClickListener = {
            showBalloonPopup(passBalloon, it, 80)
        }

        binding.loginBtn.isEnabled = false

        binding.emailTF.getTextInput()?.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int, count: Int
            ) {
                if (s.isEmpty()) {
                    binding.loginBtn?.isEnabled = false
                } else {
                    val temporary = binding.passTF?.getText().toString()
                    binding.loginBtn?.isEnabled = temporary.isNotEmpty()
                }
            }
        })

        binding.passTF?.getTextInput()?.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int, count: Int
            ) {
                if (s.isEmpty()) {
                    binding.loginBtn.isEnabled = false
                } else {
                    val temporary = binding.emailTF.getText().toString()
                    binding.loginBtn.isEnabled = temporary.isNotEmpty()
                }
            }
        })
        binding.faceIdBtn.setOnClickListener {
            showBiometricDialog()
        }

        binding.btnNotme.setOnClickListener {
            viewModel.clearUsername()
            handleUiUsername()
        }
    }

    private fun handleUiUsername() {
        val isLogined = viewModel.isLogined()
        if (!isLogined) {
            handleUINoUser()
        } else {
            val username = viewModel.getUsernameInDbLocal()
            handleUiExitedUser(username)
        }
    }

    private fun handleUiExitedUser(username: Pair<String, String>) {
        binding.emailTF.visibility = View.GONE
        binding.lnUsername.visibility = View.VISIBLE
        binding.tvUsername.text = username.first
        binding.btnNotme.visibility = View.VISIBLE
        binding.vCenter.visibility = View.VISIBLE
        binding.emailTF.setText(username.second)
        if (!hasUserConfiguredBiometric() || TextUtils.isEmpty(viewModel.getFingerID())) {
            // Chưa kích hoạt finger ID
            binding.faceIdBtn.visibility = View.GONE
        } else {
            // Đã kích hoạt fingerID
            binding.faceIdBtn.visibility = View.VISIBLE
            showBiometricDialog()
        }
    }

    private fun handleUINoUser() {
        binding.emailTF.visibility = View.VISIBLE
        binding.lnUsername.visibility = View.GONE
        binding.btnNotme.visibility = View.GONE
        binding.vCenter.visibility = View.GONE
        binding.faceIdBtn.visibility = View.GONE
    }

    private fun onLoginByBiometric() {
        disableButtonLogin()
        viewModel.loginByBiometric(viewModel.getUsernameInDbLocal().second)
    }

    //endregion
    override fun onObserverData() {
        super.onObserverData()
        dataListenerScope {
            viewModel.userBiometricResponse.handleResponse(onFail = { state ->
                if (state?.code == LOCK_TEMP || state?.code == LOCK_PERMANENT) {
                    handleLockUser(state.title, state.message, state.code)
                } else {
                    showErrorDialog(state?.title, state?.message)
                }
                enableButtonLogin()
            }) {
                registerNotification(it)
            }
        }
        dataListenerScope {
            viewModel.userResponse.handleResponse(onFail = { state ->
                enableButtonLogin()
                Timber.e("Resource.Failure")
                if (state?.code == SERVER_CODE_INVALID_USER) {
                    state.message.let { message ->
                        emailBalloon = message
                        binding.emailTF.setState(true)
                    }
                } else if (state?.code == SERVER_CODE_INVALID_PASSWORD) {
                    state.message.let { message ->
                        passBalloon = message
                        binding.passTF.setState(true)
                    }
                } else if (state?.code == LOCK_TEMP || state?.code == LOCK_PERMANENT) {
                    handleLockUser(state.title, state.message, state.code)
                } else {
                    showErrorDialog(state?.title, state?.message)
                }
            }) {
                viewModel.isShowPopupLoginWithFingerID(true)
                registerNotification(it)
            }
        }
    }


    private fun handleLockUser(title: String?, msg: String?, code: Int? = LOCK_TEMP) {
        NormalDialog.Builder(requireContext()).setImage(R.drawable.ic_deskclock, DialogType.CONFIRM)
            .setTitle(title ?: "").setMessage(msg ?: "").show(childFragmentManager)

    }

    private fun disableButtonLogin() {
        binding.progressBar?.visibility = View.VISIBLE
        binding.loginBtn?.isEnabled = false
        binding.loginBtn?.isClickable = false
    }

    private fun enableButtonLogin() {
        binding.progressBar.visibility = View.GONE
        binding.loginBtn.isEnabled = true
        binding.loginBtn.isClickable = true
    }

    private fun registerNotification(userResponse: UserResponse?) {
        if (userResponse?.data?.need_otp_confirm == true) {
            // Đang login trên 1 thiết bị khác => mở màn màn nhập otp
            onConfirmOTP()
        } else {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    hideLoading()
                    Timber.e("Fetching FCM registration token failed ${task.exception}")
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                viewModel.registerNotification(NotificationRequest(token), onError = {
                    hideLoading()
                }) {
                    hideLoading()
                    activity?.let { ctx ->
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        ctx.finish()
                    }
                }
            })
        }
    }

    private fun onConfirmOTP() {
//        findNavController().navigate(R.id.oTPConfirmInNewDeviceFragment)
    }

    override fun onSdkVersionNotSupported() {
        Timber.d("onSdkVersionNotSupported")
    }

    override fun onBiometricAuthenticationNotSupported() {
        showError(getString(R.string.tbkhtsth))

    }

    override fun onBiometricAuthenticationNotAvailable() {
        Timber.d("onBiometricAuthenticationNotAvailable")
    }

    override fun onBiometricAuthenticationPermissionNotGranted() {
        Timber.d("onBiometricAuthenticationPermissionNotGranted")
    }

    override fun onBiometricAuthenticationInternalError(error: String?) {
        Timber.d("onBiometricAuthenticationInternalError")
    }

    override fun onAuthenticationFailed() {
        Timber.d("onAuthenticationFailed")

    }

    override fun onAuthenticationCancelled() {
        Timber.d("onAuthenticationCancelled")
    }

    override fun onAuthenticationSuccessful() {
        Timber.d("onAuthenticationSuccessful")
        onLoginByBiometric()
    }


}

