package com.nikolaeva.healthdiary

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nikolaeva.healthdiary.model.ChallengeModel
import com.nikolaeva.healthdiary.model.ListModel

class ListsFragment : Fragment() {

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

        val recyclerView: RecyclerView = view.findViewById(R.id.chList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = CustomRecyclerAdapter(this, getDataList())
    }

    private fun getDataList(): List<ListModel> {
        val data = resources.getStringArray(R.array.lists).toList()
        val listModelList = mutableListOf<ListModel>()
        data.forEachIndexed { index, item ->
            listModelList.add(
                ListModel(
                    nameList = item,
                    countList = index.toString()
                )
            )
        }
        return listModelList
    }

    fun itemClick(listModel: ListModel) {
        listener?.goToDetailListFragment(listModel)
    }

    companion object {

        fun newInstance() = ListsFragment()

    }
}