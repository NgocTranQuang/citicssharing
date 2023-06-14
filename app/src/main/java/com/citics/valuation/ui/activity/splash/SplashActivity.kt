package com.citics.valuation.ui.activity.splash

import android.content.Intent
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import com.citics.cbank.R
import com.citics.cbank.databinding.ActivitySplashBinding
import citics.sharing.extension.delayOnLifecycle
import com.citics.valuation.ui.activity.login.LoginActivity
import com.citics.valuation.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {
    override val viewModel: SplashViewModel by viewModels()
    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding
        get() = ActivitySplashBinding::inflate
    private var action: String? = ""
    var fade_in_anim: Animation? = null


    override fun onConfigUI() {
        fade_in_anim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.root.startAnimation(fade_in_anim)
        super.onConfigUI()
        val bundle = intent.extras
        if (bundle != null) {
            action = bundle.getString("action")
            val offerId = bundle.getString("offer_id")
            val nPin = bundle.getString("n_pin")
            val contentId = bundle.getString("content_id")

            Timber.e("onCreate - action = $action")
            Timber.e("onCreate - offerId = $offerId")
            Timber.e("onCreate - nPin = $nPin")
            Timber.e("onCreate - contentId = $contentId")
        }
        binding.root.delayOnLifecycle(2000L) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}