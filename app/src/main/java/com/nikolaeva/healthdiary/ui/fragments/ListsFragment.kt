package com.nikolaeva.healthdiary.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.SearchView
import androidx.core.widget.addTextChangedListener
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
    private var checkListModels: List<CheckListModel> = arrayListOf()
    private lateinit var adapter: CheckListAdapter
    private lateinit var searchView: SearchView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is INavigationFragment) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.checkList)
        //searchView = view.findViewById(R.id.brSearch1)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        checkListModels = getDataList()
        userRepository.getCurrentUser(this)

        val editText = view.findViewById<EditText>(R.id.fake_search1)

        editText.addTextChangedListener(
            afterTextChanged = {
                filter(it.toString())
            },
            onTextChanged = { s, start, before, count ->

            },
            beforeTextChanged = { s, start, before, count ->

            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu)
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

    companion object {
        fun newInstance() = ListsFragment()
    }

    override fun readData(list: List<UserFirebase>) {
        val currentUser = list.firstOrNull { userFirebase -> userFirebase.uid == Firebase.auth.currentUser?.uid }
        if (currentUser != null) {
            if (!currentUser.checkList.isNullOrEmpty()) {
                userRepository.updateLocalUser(currentUser)
                checkListModels = currentUser.checkList
                adapter = CheckListAdapter(this, checkListModels)
                recyclerView.adapter = adapter
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

    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filterdNames: ArrayList<CheckListModel> = ArrayList()

        //looping through existing elements
        for (checkListModel in checkListModels) {
            //if the existing elements contains the search input
            if (checkListModel.nameCheckList.lowercase().contains(text.lowercase())) {
                //adding the element to filtered list
                filterdNames.add(checkListModel)
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }
}