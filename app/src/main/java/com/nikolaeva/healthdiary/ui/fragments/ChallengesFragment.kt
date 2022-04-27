package com.nikolaeva.healthdiary.ui.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nikolaeva.healthdiary.INavigationFragment
import com.nikolaeva.healthdiary.MainActivity
import com.nikolaeva.healthdiary.R
import com.nikolaeva.healthdiary.db.FirebaseManager
import com.nikolaeva.healthdiary.db.model.UserFirebase
import com.nikolaeva.healthdiary.model.ChallengeModel
import com.nikolaeva.healthdiary.repositories.UserRepository
import com.nikolaeva.healthdiary.ui.adapters.ChallengesAdapter
import com.nikolaeva.healthdiary.ui.adapters.IChallengesAdapter

class ChallengesFragment : Fragment(), IChallengesAdapter, FirebaseManager.ReadDataCallback {

    private var listener: INavigationFragment? = null
    private val userRepository = UserRepository.getInstance()
    private lateinit var recyclerView: RecyclerView
    private var challengeModels: List<ChallengeModel> = arrayListOf()
    private lateinit var adapter: ChallengesAdapter
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
        return inflater.inflate(R.layout.fragment_challendes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.chList)
        //searchView = view.findViewById(R.id.brSearch)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        challengeModels = getDataList()
        userRepository.getCurrentUser(this)

        val editText = view.findViewById<EditText>(R.id.fake_search)

        editText.addTextChangedListener(
            afterTextChanged = {
                filter(it.toString())
            },
            onTextChanged = {s, start, before, count->

            },
            beforeTextChanged = {s, start, before, count->

            }
        )

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu)


        // menu.findItem(R.id.).apply {
        //     setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
        //     actionView = searchView
        // }
        //
        // searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        //     override fun onQueryTextSubmit(query: String): Boolean {
        //         return false
        //     }
        //
        //     override fun onQueryTextChange(newText: String): Boolean {
        //         filter(newText)
        //         return false
        //     }
        // })
        // searchView.setOnClickListener {view ->  }
    }

    private fun getDataList(): List<ChallengeModel> {
        val data = resources.getStringArray(R.array.challenges).toList()
        val challengeModelList = mutableListOf<ChallengeModel>()
        data.forEachIndexed { index, item ->
            challengeModelList.add(
                ChallengeModel(
                    nameChallenge = item,
                    countChallenge = 0.toString()
                )
            )
        }
        return challengeModelList
    }

    override fun itemClick(challengeModel: ChallengeModel) {
        listener?.goToDetailChallengeFragment(challengeModel)
    }

    companion object {

        fun newInstance() = ChallengesFragment()
    }

    override fun readData(list: List<UserFirebase>) {
        val currentUser = list.firstOrNull { userFirebase -> userFirebase.uid == Firebase.auth.currentUser?.uid }
        if (currentUser != null) {
            if (currentUser.challenges != null) {
                userRepository.updateLocalUser(currentUser)
                challengeModels = currentUser.challenges
                adapter = ChallengesAdapter(this, challengeModels)
                recyclerView.adapter = adapter
            } else {
                val userFirebase = userRepository.createUserFirebase(challenges = challengeModels)
                userRepository.addUser(userFirebase)
                userRepository.getCurrentUser(this)
            }
        } else {
            val userFirebase = userRepository.createUserFirebase(challenges = challengeModels)
            userRepository.addUser(userFirebase)
            adapter = ChallengesAdapter(this, challengeModels)
            recyclerView.adapter = adapter
        }
    }

    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filterdNames: ArrayList<ChallengeModel> = ArrayList()

        //looping through existing elements
        for (challengeModel in challengeModels) {
            //if the existing elements contains the search input
            if (challengeModel.nameChallenge.lowercase().contains(text.lowercase())) {
                //adding the element to filtered list
                filterdNames.add(challengeModel)
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