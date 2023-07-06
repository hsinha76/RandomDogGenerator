package com.hsdroid.randomdoggenerator.utils

import android.content.Context

class LRUCache(
    val context: Context,
    private val size: Int,
    private val sharedPreferenceUtil : SharedPreferenceUtil
) {

    val lruCache: MutableList<String> = mutableListOf()

    fun onRestart() {
        if (lruCache.size >= size) {
            lruCache.removeAt(0)
        }

        if (sharedPreferenceUtil.getStringPreference(Constants.CACHE_LIST, null) != null) {
            val replacement = sharedPreferenceUtil.getStringPreference(Constants.CACHE_LIST, null)
                .replace("[", "")
                .replace("]", "")
            lruCache.add(replacement)
        }
    }

    fun putData(url: String) {
        if (lruCache.size >= size) {
            lruCache.removeAt(0)
        }

        lruCache.add(url)

        sharedPreferenceUtil.setStringPreference(Constants.CACHE_LIST, lruCache.toString())

    }

    fun displayData(): List<String> {
        return lruCache
    }
}