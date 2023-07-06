package com.hsdroid.randomdoggenerator.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hsdroid.randomdoggenerator.R
import com.hsdroid.randomdoggenerator.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        init()
        return binding.root
    }

    private fun init() {
        binding.btnGenerateDogs.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_generateFragment)
        }

        binding.btnViewGeneratedDogs.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_recentlyGeneratedFragment)
        }

    }
}