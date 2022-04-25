package com.nikolaeva.healthdiary.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nikolaeva.healthdiary.R
import com.nikolaeva.healthdiary.model.CheckListModel

class CheckListAdapter(
    private val listener: ICheckListAdapter,
    private val checkListModel: List<CheckListModel>
) : RecyclerView.Adapter<CheckListAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.textViewLarge)
        val count: TextView = itemView.findViewById(R.id.textViewSmall)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = checkListModel[position].nameCheckList
        //holder.count.text = checkListModel[position].count

        holder.itemView.setOnClickListener {
            listener.itemClick(checkListModel[position])

        }
    }

    override fun getItemCount() = checkListModel.size


}

interface ICheckListAdapter {
    fun itemClick(checkListModel: CheckListModel)
}