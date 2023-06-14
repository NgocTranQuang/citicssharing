package com.citics.valuation.ui.activity.chitietcongtrinh

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import citics.sharing.data.model.response.AssetDetailData
import com.citics.cbank.databinding.ActivityChitietcongtrinhBinding
import citics.sharing.extension.getData
import com.citics.valuation.ui.base.BaseActivity
import citics.sharing.utils.KEY_BUNDLE_DATA
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by ChinhQT on 04/10/2022.
 */
@AndroidEntryPoint
class ChiTietCongTrinhActivity :
    BaseActivity<ActivityChitietcongtrinhBinding, ChiTietCongTrinhViewModel>() {

    override val viewModel: ChiTietCongTrinhViewModel by viewModels()

    override fun onConfigUI() {
        super.onConfigUI()
        val bundle = intent?.getBundleExtra(KEY_BUNDLE_DATA)
        val asset = bundle?.getData<AssetDetailData>(KEY_ASSET_DETAIL)
//        val position = bundle?.getInt(KEY_POSITION_CONGTRINH) ?: -1
        asset?.let {
            viewModel.setAsset(it)
        }
//        asset?.properties?.cong_trinh?.getOrNull(position)?.let {
//            viewModel.setCongTrinh(it)
//        } ?: kotlin.run {
//            viewModel.setCongTrinh(CongTrinh())
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    companion object {
        const val KEY_ASSET_DETAIL = "KEY_ASSET_DETAIL"
        const val KEY_ASSET_EXTRA = "KEY_ASSET_EXTRA"
        private const val KEY_POSITION_CONGTRINH =
            "KEY_POSITION_CONGTRINH" /*-1 => Tạo mới, != -1 => Edit*/

        fun newIntent(context: Context, asset: AssetDetailData?): Intent {
            val intent = Intent(context, ChiTietCongTrinhActivity::class.java)
            intent.putExtra(
                KEY_BUNDLE_DATA,
                bundleOf(KEY_ASSET_DETAIL to asset)
            )
            return intent
        }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityChitietcongtrinhBinding
        get() = ActivityChitietcongtrinhBinding::inflate

}