package com.hsdroid.randomdoggenerator.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hsdroid.randomdoggenerator.databinding.ItemsDogBinding

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    private var list: List<String> ? = null
    private lateinit var binding: ItemsDogBinding

    inner class MyViewHolder(val binding: ItemsDogBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecyclerAdapter.MyViewHolder {
        binding = ItemsDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.MyViewHolder, position: Int) {

        val imageUrl = list!![position]

        with(holder) {
            Glide.with(itemView.context).load(imageUrl).into(binding.imgFetch)
        }
    }

    override fun getItemCount(): Int {
        if(list==null) return 0
        return list!!.size
    }

    fun setList(list: List<String>) {
        this.list = list
        notifyDataSetChanged()
    }
}