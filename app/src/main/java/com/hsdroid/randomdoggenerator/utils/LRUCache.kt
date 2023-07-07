package com.hsdroid.randomdoggenerator.utils

import android.content.Context
import android.util.Log

class LRUCache(
    val context: Context,
    private val size: Int,
    private val sharedPreferenceUtil: SharedPreferenceUtil
) {

    val lruCache: MutableList<String> = mutableListOf()

    fun onRestart() {

        if (sharedPreferenceUtil.getStringPreference(Constants.CACHE_LIST, null) != null) {
            val replacement = sharedPreferenceUtil.getStringPreference(Constants.CACHE_LIST, null)
                .replace("[", "")
                .replace("]", "")

            lruCache.addAll(replacement.split(", "))
        }

        Log.d("harish", "on start $lruCache")
    }

    fun putData(url: String) {

        val itemsString = sharedPreferenceUtil.getStringPreference(Constants.CACHE_LIST, null)

        val itemCount = itemsString?.trim('[', ']') // Remove leading and trailing brackets
            ?.split(", ") // Split the string into individual items
            ?.map { it.trim() } // Remove any leading or trailing whitespace
            ?.size ?: 0

        lruCache.add(url)

        if (itemCount >= size) {
            Log.d("harish", "element at 0 " + lruCache.elementAt(0))
            lruCache.removeAt(0)
        }

        sharedPreferenceUtil.setStringPreference(Constants.CACHE_LIST, lruCache.toString())
    }

    fun displayData(): List<String> {
        return lruCache
    }
}