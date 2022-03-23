package com.nikolaeva.healthdiary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nikolaeva.healthdiary.model.ChallengeModel

class CustomRecyclerAdapter(
    private val listener: ICustomRecyclerAdapter,
    private val challengeModels: List<ChallengeModel>
) : RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val largeTextView: TextView = itemView.findViewById(R.id.textViewLarge)
        val smallTextView: TextView = itemView.findViewById(R.id.textViewSmall)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.largeTextView.text = challengeModels[position].nameChallenge
        holder.smallTextView.text = challengeModels[position].countChallenge
        holder.itemView.setOnClickListener {
            listener.itemClick(challengeModels[position])
        }
    }

    override fun getItemCount() = challengeModels.size
}

interface ICustomRecyclerAdapter {
    fun itemClick(challengeModel: ChallengeModel)
}