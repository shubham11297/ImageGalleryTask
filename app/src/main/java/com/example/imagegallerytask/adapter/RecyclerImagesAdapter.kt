package com.example.imagegallerytask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.imagegallerytask.R

class RecyclerImagesAdapter(val context: Context, val imagesList: MutableList<String>) : RecyclerView.Adapter<RecyclerImagesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val v: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide
            .with(context)
            .load(imagesList[position])
            .placeholder(R.drawable.loadingshimmersq)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<AppCompatImageView>(R.id.image)
    }

    fun updateRecycler(list: MutableList<String>){
        val oldSize = imagesList.size
        imagesList.addAll(list)
        val newSize = imagesList.size
        notifyItemRangeChanged(oldSize, newSize)
    }
}