package com.nikolaeva.healthdiary.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CLAdapter : RecyclerView.Adapter<CLAdapter.MyViewHolder>() {
    private var checkList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_super_heroes, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(checkList[position])
    }

    override fun getItemCount(): Int {
        return checkList.size
    }

    fun setData(superHeroes: ArrayList<String>) {
        checkList = superHeroes
        notifyDataSetChanged()
    }

    //to filter the list
    fun filterList(superHeroNames: ArrayList<String>) {
        this.checkList = superHeroNames
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemSuperHeroesBinding.bind(itemView)

        fun bind(superHero: String) {
            binding.tvName.text = superHero
        }
    }
}