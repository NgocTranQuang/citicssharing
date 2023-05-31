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
        preferenceManager.jwtToken =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzMjAyMjEwMjExMTE2MDgwMDMwMDEiLCJhdXRoIjoiUk9MRV9BR0VOQ1lfTDEsUk9MRV9BR0VOQ1lfUFJPQ19SRUNPUkRfTDEsUk9MRV9BR0VOVCIsInVzZXJfaWQiOiIzMjAyMjEwMjExMTE2MDgwMDMwMDEiLCJ1c2VyX25hbWUiOiJjdW9uZ3Z4QGNpdGljcy52biIsImxvbmdpdHVkZSI6MTA2LjcwNjI2MzcsImxhdGl0dWRlIjoxMC43NjAzMTU0LCJDVEMtUGxhdGZvcm0iOiJXZWIiLCJVc2VyLUFnZW50IjoiUG9zdG1hblJ1bnRpbWUvNy4zMi4yIiwiRGV2aWNlLU5hbWUiOiI0NTQ1NjQ1NiIsIkRldmljZS1JRCI6IjEyMzE0NDQ0IiwiZXhwIjoxNjg1NTg2NjEyfQ.ZLeb1HRSPzBk41rgFqEQsx6KQc5XF-5JWVKQ7_oaBwGwM5gknsjwXWUEpz78rxzv5q5dKkU2k_bf0dfa4wi1Yw"
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