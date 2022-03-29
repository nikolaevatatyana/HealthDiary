package com.nikolaeva.healthdiary.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nikolaeva.healthdiary.INavigationFragment
import com.nikolaeva.healthdiary.R
import com.nikolaeva.healthdiary.model.CheckListModel
import com.nikolaeva.healthdiary.ui.adapters.CheckListAdapter
import com.nikolaeva.healthdiary.ui.adapters.ICheckListAdapter

class ListsFragment : Fragment(), ICheckListAdapter {

    private var listener: INavigationFragment? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is INavigationFragment) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.checkList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = CheckListAdapter(this, getDataList())
    }

    private fun getDataList(): List<CheckListModel> {
        val data = resources.getStringArray(R.array.lists).toList()
        val listModelList = mutableListOf<CheckListModel>()
        data.forEachIndexed { index, item ->
            listModelList.add(
                CheckListModel(
                    name = item,
                    count = index.toString()
                )
            )
        }
        return listModelList
    }

    override fun itemClick(checkListModel: CheckListModel) {
        listener?.goToDetailListFragment(checkListModel)
    }

    companion object {

        fun newInstance() = ListsFragment()
    }
}