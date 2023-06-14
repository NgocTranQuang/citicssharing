package com.citics.valuation.ui.activity.tracuu

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.citics.cbank.databinding.ActivityChitietcanhoBinding
import com.citics.valuation.extension.toJson
import com.citics.valuation.extension.toObject
import com.citics.valuation.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChiTietCanHoActivity :
    BaseActivity<ActivityChitietcanhoBinding, ChiTietTaiSanTraCuuViewModel>() {
    override val viewModel: ChiTietTaiSanTraCuuViewModel by viewModels()
    override val bindingInflater: (LayoutInflater) -> ActivityChitietcanhoBinding
        get() = ActivityChitietcanhoBinding::inflate

    companion object {
        private const val KEY_HAS_MAP = "KEY_HAS_MAP"
        fun newIntent(context: Context, map: HashMap<String, Any?>): Intent {
            val intent = Intent(context, ChiTietCanHoActivity::class.java)
            intent.putExtra(KEY_HAS_MAP, map.toJson())
            return intent
        }
    }

    override fun onConfigUI() {
        super.onConfigUI()
        val hasMap = intent?.getStringExtra(KEY_HAS_MAP)
        val map = hasMap?.toObject<HashMap<String, Any?>>()
        map?.let {
            viewModel.getCanHoDetail(map)
        }
    }
}