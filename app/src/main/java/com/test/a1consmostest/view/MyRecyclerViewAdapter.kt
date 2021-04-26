package com.test.a1consmostest.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.a1consmostest.R
import com.test.a1consmostest.databinding.RecyclerItemBinding
import com.test.a1consmostest.model.Data

class MyRecyclerViewAdapter(var context: Context, var list: ArrayList<Data>) : RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {

    // Inflating Layout and ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RecyclerItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recycler_item, parent, false)
        return ViewHolder(binding)
    }


    fun updateItemList( newlist: ArrayList<Data>)
    {
        list.addAll(newlist)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = list.size

    // Bind data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list.get(position))

        Glide.with(context)
            .load(list.get(position).avatar)
            .centerCrop()
            .into(holder.binding.avator);
    }

    // Creating ViewHolder
    class ViewHolder(val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.user=data
            binding.executePendingBindings()
        }
    }
}