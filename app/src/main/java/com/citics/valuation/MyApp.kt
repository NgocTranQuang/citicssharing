package com.citics.valuation

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.provider.Settings
import androidx.appcompat.app.AppCompatDelegate
import com.citics.cbank.BuildConfig
import com.citics.valuation.data.datasource.local.PreferenceManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Timber.d("onCreate")
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val id = getDeviceId(this)
        preferenceManager.deviceID = id ?: ""
    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String? {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Timber.d("onTrimMemory")
    }
}