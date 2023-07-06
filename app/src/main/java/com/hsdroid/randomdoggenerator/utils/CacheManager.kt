package com.hsdroid.randomdoggenerator.utils

import android.annotation.SuppressLint
import android.content.Context

object CacheManager {
    @SuppressLint("StaticFieldLeak")
    private var lruCacheInstance: LRUCache? = null
    private var cacheSize: Int = 20 //Will hold 20 recent images

    fun getLRUCache(context: Context, sharedPreferenceUtil: SharedPreferenceUtil): LRUCache {
        if (lruCacheInstance == null) {
            lruCacheInstance = LRUCache(context, cacheSize, sharedPreferenceUtil)
        }
        return lruCacheInstance!!
    }
}
