package com.hsdroid.randomdoggenerator.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hsdroid.randomdoggenerator.databinding.FragmentRecentlyGeneratedBinding
import com.hsdroid.randomdoggenerator.ui.adapter.RecyclerAdapter
import com.hsdroid.randomdoggenerator.utils.CacheManager
import com.hsdroid.randomdoggenerator.utils.Constants
import com.hsdroid.randomdoggenerator.utils.LRUCache
import com.hsdroid.randomdoggenerator.utils.SharedPreferenceUtil


class RecentlyGeneratedFragment : Fragment() {
    private lateinit var binding: FragmentRecentlyGeneratedBinding
    private lateinit var sharedPreferenceUtil: SharedPreferenceUtil
    private val recyclerAdapter: RecyclerAdapter by lazy {
        RecyclerAdapter()
    }
    private lateinit var cache : LRUCache


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecentlyGeneratedBinding.inflate(layoutInflater)

        init()
        return binding.root
    }

    private fun init() {
        sharedPreferenceUtil = SharedPreferenceUtil(requireContext())
        cache = CacheManager.getLRUCache(requireContext(),sharedPreferenceUtil)

        binding.btnClearList.setOnClickListener {
            sharedPreferenceUtil.removePreference(Constants.CACHE_LIST)
            recyclerAdapter.setList(emptyList())
            cache.lruCache.clear()
        }

        refactorList()
        initRecyclerView()
    }

    private fun refactorList() {
        val list = sharedPreferenceUtil.getStringPreference(Constants.CACHE_LIST, null)
        if (list != null) {
            val imageUrlList = list
                .removeSurrounding("[", "]") // Remove square brackets
                .split(", ") // Split into individual elements

            Log.d("harish", imageUrlList.toString())
            recyclerAdapter.setList(imageUrlList)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            adapter = recyclerAdapter
        }
    }

}