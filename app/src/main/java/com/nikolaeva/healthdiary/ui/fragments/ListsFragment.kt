package com.nikolaeva.healthdiary.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nikolaeva.healthdiary.INavigationFragment
import com.nikolaeva.healthdiary.R
import com.nikolaeva.healthdiary.db.FirebaseManager
import com.nikolaeva.healthdiary.db.model.UserFirebase
import com.nikolaeva.healthdiary.model.ChallengeModel
import com.nikolaeva.healthdiary.model.CheckListModel
import com.nikolaeva.healthdiary.repositories.UserRepository
import com.nikolaeva.healthdiary.ui.adapters.ChallengesAdapter
import com.nikolaeva.healthdiary.ui.adapters.CheckListAdapter
import com.nikolaeva.healthdiary.ui.adapters.ICheckListAdapter

class ListsFragment : Fragment(), ICheckListAdapter, FirebaseManager.ReadDataCallback {

    private var listener: INavigationFragment? = null
    private val userRepository = UserRepository.getInstance()
    private lateinit var recyclerView: RecyclerView

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

        recyclerView = view.findViewById(R.id.checkList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        userRepository.getCurrentUser(this)
    }

    private fun getDataList(): List<CheckListModel> {
        val data = resources.getStringArray(R.array.lists).toList()
        val listModelList = mutableListOf<CheckListModel>()
        data.forEachIndexed { index, item ->
            listModelList.add(
                CheckListModel(
                    nameCheckList = item,
                    listDate = listOf("20.01.2021", "21.01.2021")
                )
            )
        }
        return listModelList
    }

    override fun itemClick(checkListModel: CheckListModel) {
        listener?.goToDetailListFragment(checkListModel)
    }

    override fun readData(list: List<UserFirebase>) {
        val currentUser = list.firstOrNull { userFirebase -> userFirebase.uid == Firebase.auth.currentUser?.uid }
        if (currentUser != null) {
            if (currentUser.checkList != null) {
                userRepository.updateLocalUser(currentUser)
                recyclerView.adapter = CheckListAdapter(this, currentUser.checkList)
            } else {
                val userFirebase = userRepository.createUserFirebase(checkList = getDataList())
                userRepository.addUser(userFirebase)
                userRepository.getCurrentUser(this)
            }
        } else {
            val userFirebase = userRepository.createUserFirebase(checkList = getDataList())
            userRepository.addUser(userFirebase)
            recyclerView.adapter = CheckListAdapter(this, getDataList())
        }
    }

    companion object {
        fun newInstance() = ListsFragment()
    }
}