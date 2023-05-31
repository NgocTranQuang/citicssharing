package com.citics.valuation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.citics.valuation.data.model.response.ErrorResponse
import com.citics.valuation.data.repository.Resource
import com.citics.valuation.utils.DialogUtils
import com.citics.valuation.utils.SERVER_CODE_INVALID_C_POINT_408
import com.citics.valuation.utils.SERVER_CODE_INVALID_C_POINT_409
import com.citics.valuation.utils.SERVER_CODE_LOGIN_IN_NEW_DEVICE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.citics.cbank.R

abstract class BaseFragment<V : ViewBinding, VM : BaseViewModel> : Fragment() {

    private var _binding: V? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    abstract val viewModel: VM


    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onConfigUI()
        onObserverData()
    }

    open fun onObserverData() {
    }


    fun showErrorDialog(
        title: String? = getString(R.string.system_error),
        smg: String?
    ) {
        (requireActivity() as? BaseActivity<*, *>)?.showErrorDialog(title, smg)
    }

    fun showError(message: String) {
        (requireActivity() as? BaseActivity<*, *>)?.showError(message)
    }

    fun showSuccess(message: String) {
        (requireActivity() as? BaseActivity<*, *>)?.showSuccess(message)
    }

    fun showInfo(message: String) {
        (requireActivity() as? BaseActivity<*, *>)?.showInfo(message)
    }

    fun showLoading() {
        (requireActivity() as? BaseActivity<*, *>)?.showLoading()
    }

    fun hideLoading() {
        (requireActivity() as? BaseActivity<*, *>)?.hideLoading()
    }

    open fun onConfigUI() {
        (requireActivity() as? BaseActivity<*, *>)?.setupUI(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun dataListenerScope(listenerData: suspend CoroutineScope.() -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
//        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            listenerData.invoke(this)
//        }
        }
    }

    suspend fun <T> StateFlow<Resource<T>>.handleResponse(
        onLoading: (() -> Unit)? = null,
        onFail: (suspend ((ErrorResponse?) -> Unit))? = null,
        onSuccess: suspend (T?) -> Unit
    ) {
        (activity as BaseActivity<*, *>).apply {
            handleResponse(onLoading, onFail, onSuccess)
        }
    }

    suspend fun <T> StateFlow<Resource<T>>.handleResponseOnce(
        onLoading: (() -> Unit)? = null,
        onFail: (suspend ((ErrorResponse?) -> Unit))? = null,
        onSuccess: suspend (T?) -> Unit
    ) {

        (activity as BaseActivity<*, *>).apply {
            handleResponseOnce(onLoading, onFail, onSuccess)
        }
    }


}