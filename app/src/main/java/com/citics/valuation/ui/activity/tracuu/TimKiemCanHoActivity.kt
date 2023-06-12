package com.citics.valuation.ui.activity.tracuu

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.citics.cbank.databinding.ActivityChitietduanBinding
import com.citics.cbank.databinding.ActivityChitietnhadatBinding
import com.citics.cbank.databinding.ActivityTimkiemcanhoBinding
import com.citics.valuation.ui.base.BaseActivity
import com.citics.valuation.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimKiemCanHoActivity : BaseActivity<ActivityTimkiemcanhoBinding, TimKiemCanHoViewModel>() {
    override val viewModel: TimKiemCanHoViewModel by viewModels()
    override val bindingInflater: (LayoutInflater) -> ActivityTimkiemcanhoBinding
        get() = ActivityTimkiemcanhoBinding::inflate

    companion object {
        private const val KEY_PROJECT_ID = "KEY_PROJECT_ID"
        fun newIntent(context: Context, projectID: String): Intent {
            val intent = Intent(context, TimKiemCanHoActivity::class.java)
            intent.putExtra(KEY_PROJECT_ID, projectID)
            return intent
        }
    }

    override fun onConfigUI() {
        super.onConfigUI()
        val projectID = intent.getStringExtra(KEY_PROJECT_ID)
        viewModel.setProjectID(projectID ?: "")
    }
}