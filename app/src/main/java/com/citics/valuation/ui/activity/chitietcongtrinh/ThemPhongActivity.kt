package com.citics.valuation.ui.activity.chitietcongtrinh

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.citics.cagent.data.model.response.CongTrinh
import com.citics.cbank.databinding.FragmentThemLoaiPhongBinding
import citics.sharing.extension.getData
import com.citics.valuation.ui.base.BaseActivity
import com.citics.valuation.utils.KEY_BUNDLE_DATA
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by ChinhQT on 04/10/2022.
 */
@AndroidEntryPoint
class ThemPhongActivity :
    BaseActivity<FragmentThemLoaiPhongBinding, ChiTietCongTrinhViewModel>() {

    override val viewModel: ChiTietCongTrinhViewModel by viewModels()
    private var room: CongTrinh.ExtraRooms = CongTrinh.ExtraRooms()

    companion object {
        const val DATA_THEM_PHONG_MOI = "DATA_THEM_PHONG_MOI"

        //        const val KEY_ASSET_BUNDLE = "KEY_ASSET_BUNDLE"
        fun getArgument(room: CongTrinh.ExtraRooms?): Bundle {
            return bundleOf(DATA_THEM_PHONG_MOI to room)
        }

        fun newIntent(context: Context, room: CongTrinh.ExtraRooms?): Intent {
            val intent = Intent(context, ChiTietCongTrinhActivity::class.java)
            intent.putExtra(KEY_BUNDLE_DATA, getArgument(room))
            return intent
        }
    }

    override fun onConfigUI() {
        super.onConfigUI()
        val bundle = intent?.getBundleExtra(KEY_BUNDLE_DATA)
        val room = bundle?.getData<CongTrinh.ExtraRooms>(DATA_THEM_PHONG_MOI)
        room?.let {
            this@ThemPhongActivity.room = it
        }
        binding.vSoPhong.setText(room?.so_phong ?: 0) {
            room?.so_phong = it
            checkEnableButton()
        }
        binding.vTenPhong.setText(room?.title ?: "") {
            room?.title = it
            checkEnableButton()
        }
    }


    override fun onClickListener() {
        super.onClickListener()
        binding.btnTiepTuc.setOnClickListener {
            val intent = Intent()
            intent.putExtra(KEY_BUNDLE_DATA, bundleOf(DATA_THEM_PHONG_MOI to room))
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private fun checkEnableButton() {
        if (TextUtils.isEmpty(
                binding.vSoPhong.getText().toString()
            ) || TextUtils.isEmpty(binding.vTenPhong.getText().toString())
        ) {
            binding.btnTiepTuc.disableButton()
        } else {
            binding.btnTiepTuc.enableButton()
        }
    }


    override val bindingInflater: (LayoutInflater) -> FragmentThemLoaiPhongBinding
        get() = FragmentThemLoaiPhongBinding::inflate

}