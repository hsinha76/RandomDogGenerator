package com.hsdroid.randomdoggenerator.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.hsdroid.randomdoggenerator.ui.viewmodel.DogsViewmodel
import com.hsdroid.randomdoggenerator.databinding.FragmentGenerateBinding
import com.hsdroid.randomdoggenerator.utils.ApiState
import com.hsdroid.randomdoggenerator.utils.CacheManager
import kotlinx.coroutines.launch
import com.bumptech.glide.request.target.Target
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import androidx.vectordrawable.animated.R
import com.hsdroid.randomdoggenerator.utils.Constants
import com.hsdroid.randomdoggenerator.utils.LRUCache
import com.hsdroid.randomdoggenerator.utils.SharedPreferenceUtil

class GenerateFragment : Fragment() {
    private val viewmodel: DogsViewmodel by activityViewModels()
    private lateinit var binding: FragmentGenerateBinding
    private lateinit var sharedPreferenceUtil: SharedPreferenceUtil
    private lateinit var cache: LRUCache

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGenerateBinding.inflate(layoutInflater)
        init()

        return binding.root
    }

    private fun init() {
        sharedPreferenceUtil = SharedPreferenceUtil(requireContext())
        cache = CacheManager.getLRUCache(requireContext(), sharedPreferenceUtil)

        binding.btnGenerate.setOnClickListener {
            setGenerateButtonEnabled(false)
            getData()
        }
    }

    private fun getData() {
        viewmodel.getData()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel._response.collect {
                    when (it) {
                        is ApiState.SUCCESS -> loadImage(it.data)
                        is ApiState.FAILURE -> Toast.makeText(
                            context, it.t.toString(), Toast.LENGTH_SHORT
                        ).show().also {
                            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                                setGenerateButtonEnabled(true)
                            }, 500)
                        }

                        else -> ApiState.EMPTY.also {
                            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                                setGenerateButtonEnabled(true)
                            }, 500)
                        }
                    }
                }
            }
        }
    }

    private fun loadImage(data: String) {

        Glide.with(requireContext()).load(data).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                p0: GlideException?, p1: Any?, p2: Target<Drawable>?, p3: Boolean
            ): Boolean {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                setGenerateButtonEnabled(true)
                return false
            }

            override fun onResourceReady(
                p0: Drawable?, p1: Any?, p2: Target<Drawable>?, p3: DataSource?, p4: Boolean
            ): Boolean {
                //do something when picture already loaded
                cache.putData(data)
                setGenerateButtonEnabled(true)
                return false
            }
        }).into(binding.imgFetch)
    }

    private fun setGenerateButtonEnabled(isEnabled: Boolean) {
        binding.btnGenerate.isEnabled = isEnabled
    }

}