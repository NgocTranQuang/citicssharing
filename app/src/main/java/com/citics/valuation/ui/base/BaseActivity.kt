package com.citics.valuation.ui.base

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.citics.valuation.data.model.response.ErrorResponse
import com.citics.valuation.data.repository.Resource
import com.citics.valuation.ui.dialog.LoadingDialog
import com.citics.valuation.ui.dialog.NormalDialog
import com.citics.valuation.utils.*
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import com.citics.cbank.R

abstract class BaseActivity<V : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {
    lateinit var binding: V
    abstract val viewModel: VM
    private var loadingDialog: LoadingDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflater(layoutInflater)
        setContentView(binding.root)
        onConfigUI()
        onObserverData()
    }

    open fun onObserverData() {
        dataListenerScope {
            viewModel.showErrorDialog.collect {
                it?.let {
                    showErrorDialog(it?.message, it?.title)
                }
            }
        }
        dataListenerScope {
            viewModel.showLoading.collect {
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }
        dataListenerScope {
            viewModel.showErrorHetCPoint.collect {
                it?.let {
                    DialogUtils.showDialogHetCPoint(this@BaseActivity, supportFragmentManager)
                }
            }
        }
    }

    open fun onConfigUI() {
        setupUI(binding.root)
    }

    fun showError(message: String) {
        Toasty.error(this, message, Toast.LENGTH_SHORT, true).show()
    }

    fun showSuccess(message: String) {
        Toasty.success(this, message, Toast.LENGTH_SHORT, true).show()
    }

    fun showInfo(message: String) {
        Toasty.info(this, message, Toast.LENGTH_SHORT, true).show()
    }

    fun showLoading() {
        if (loadingDialog?.isShowing() == true) return
        loadingDialog = LoadingDialog.getInstance()
        loadingDialog?.show(this.supportFragmentManager, LoadingDialog.TAG)
    }

    fun hideLoading() {
        if (loadingDialog?.isShowing() == true) {
            loadingDialog?.dismiss()
            loadingDialog = null
        }
    }


    open fun dataListenerScope(listenerData: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                listenerData.invoke(this)
            }
        }
    }


    fun showErrorDialog(
        title: String? = getString(R.string.system_error),
        smg: String?
    ) {
        var newTitle = title
        if (TextUtils.isEmpty(title)) {
            newTitle = getString(R.string.system_error)
        }
        let {
            NormalDialog.Builder(it).setImage(R.drawable.ic_warning, DialogType.ERROR)
                .setTitle(newTitle.toString()).setMessage(smg ?: "")
                .setPositiveButton(R.string.i_got_it) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }.show(it.supportFragmentManager)
        }
    }

    suspend fun <T> StateFlow<Resource<T>>.handleResponse(
        onLoading: (() -> Unit)? = null,
        onFail: (suspend ((ErrorResponse?) -> Unit))? = null,
        onSuccess: suspend (T?) -> Unit
    ) {
        collect {
            when (it) {
                is Resource.Loading -> {
                    onLoading?.invoke()
                }
                is Resource.Failure -> {
                    hideLoading()
                    if (it.error?.code == SERVER_CODE_INVALID_C_POINT_408 || it.error?.code == SERVER_CODE_INVALID_C_POINT_409) {
                        (this as MutableStateFlow<Resource<T>>).emit(Resource.None())
                        DialogUtils.showDialogHetCPoint(this@BaseActivity, supportFragmentManager)
                    } else if (it.error?.code == SERVER_CODE_LOGIN_IN_NEW_DEVICE) {
                        (this as MutableStateFlow<Resource<T>>).emit(Resource.None())
                        onLoginInNewDevice()
                    } else {
                        if (onFail != null) {
                            onFail.invoke(it.error)
                        } else {
                            showErrorDialog(it.error?.title, it.error?.message)
                        }
                    }
                }

                is Resource.Success -> {
                    onSuccess.invoke(it.data)
                }
                is Resource.None -> {

                }
            }
        }
    }

    private fun onLoginInNewDevice() {
//                        val mIntent = Intent(this@BaseActivity, LoginActivity::class.java)
//                        mIntent.putExtra(
//                            LoginActivity.KEY_LOGIN_IN_NEW_DEVICE_MESSAGE,
//                            it.dataFail?.message
//                        )
//                        mIntent.putExtra(
//                            LoginActivity.KEY_LOGIN_IN_NEW_DEVICE_TITLE,
//                            it.dataFail?.title
//                        )
//                        mIntent.flags =
//                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        startActivity(mIntent)
    }

    suspend fun <T> StateFlow<Resource<T>>.handleResponseOnce(
        onLoading: (() -> Unit)? = null,
        onFail: (suspend ((ErrorResponse?) -> Unit))? = null,
        onSuccess: suspend (T?) -> Unit
    ) {
        handleResponse(onLoading, onFail = {
            onFail?.invoke(it) ?: kotlin.run {
                hideLoading()
                showErrorDialog(it?.message, it?.title)
            }
            (this as MutableStateFlow<Resource<T>>).emit(Resource.None())
        }) {
            onSuccess.invoke(it)
            (this as MutableStateFlow<Resource<T>>).emit(Resource.None())
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    open fun setupUI(view: View?) {
        try {
            // Set up touch listener for non-text box views to hide keyboard.
            if (view !is EditText) {
                view?.setOnTouchListener { v, event ->
                    this.run {
                        hideSoftKeyboard(this)
                    }
                    false
                }
            }

            //If a layout container, iterate over children and seed recursion.
            if (view is ViewGroup) {
                for (i in 0 until view.childCount) {
                    val innerView = view.getChildAt(i)
                    setupUI(innerView)
                }
            }

        } catch (e: Exception) {
            Timber.d("${e.message}")
        }

    }

    open fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus?.windowToken,
                0
            )
        }
    }

    abstract val bindingInflater: (LayoutInflater) -> V

}