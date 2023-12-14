package com.example.smm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smm.databinding.ItemViewPagerBinding
import com.example.smm.models.MySmm
import com.squareup.picasso.Picasso


class MySmmAdapter(var list:ArrayList<MySmm> = ArrayList()): RecyclerView.Adapter<MySmmAdapter.Vh>() {

    inner class Vh(val itemRv: ItemViewPagerBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(mySmm: MySmm) {
        itemRv.tvItem.text = mySmm.name
            Picasso.get().load(mySmm.imageLink).into(itemRv.imageItem)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }
}
