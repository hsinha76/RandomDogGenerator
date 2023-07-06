package com.hsdroid.randomdoggenerator

import android.app.Application
import android.util.Log
import com.hsdroid.randomdoggenerator.utils.CacheManager
import com.hsdroid.randomdoggenerator.utils.Constants
import com.hsdroid.randomdoggenerator.utils.SharedPreferenceUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    private lateinit var sharedPreferenceUtil: SharedPreferenceUtil

    override fun onCreate() {
        super.onCreate()
        sharedPreferenceUtil = SharedPreferenceUtil(applicationContext)

        val restoreData = CacheManager.getLRUCache(applicationContext, sharedPreferenceUtil)
        if (sharedPreferenceUtil.getStringPreference(Constants.CACHE_LIST, null) != null) {
            restoreData.onRestart()
        }

    }
}