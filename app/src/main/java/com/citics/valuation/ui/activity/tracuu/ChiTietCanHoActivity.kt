package com.citics.valuation.ui.activity.tracuu

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.citics.cbank.databinding.ActivityChitietcanhoBinding
import com.citics.cbank.databinding.ActivityChitietduanBinding
import com.citics.cbank.databinding.ActivityChitietnhadatBinding
import com.citics.valuation.ui.base.BaseActivity
import com.citics.valuation.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChiTietCanHoActivity : BaseActivity<ActivityChitietcanhoBinding, ChiTietNhaDatViewModel>() {
    override val viewModel: ChiTietNhaDatViewModel by viewModels()
    override val bindingInflater: (LayoutInflater) -> ActivityChitietcanhoBinding
        get() = ActivityChitietcanhoBinding::inflate

    companion object {
        private const val KEY_PROJECT_ID = "KEY_PROJECT_ID"
        private const val KEY_MA_CAN_HO = "KEY_MA_CAN_HO"
        fun newIntent(context: Context, projectID: String, macanho: String): Intent {
            val intent = Intent(context, ChiTietCanHoActivity::class.java)
            intent.putExtra(KEY_PROJECT_ID, projectID)
            intent.putExtra(KEY_MA_CAN_HO, macanho)
            return intent
        }
    }

    override fun onConfigUI() {
        super.onConfigUI()
        val macanho = intent?.getStringExtra(KEY_MA_CAN_HO) ?: ""
        val projectID = intent?.getStringExtra(KEY_PROJECT_ID) ?: ""
        viewModel.getCanHoDetail(projectID, macanho)
    }
}