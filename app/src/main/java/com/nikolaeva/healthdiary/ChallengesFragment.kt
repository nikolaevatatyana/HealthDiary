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

class ChallengesFragment : Fragment(), ICustomRecyclerAdapter {

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
        return inflater.inflate(R.layout.fragment_challendes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.chList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = CustomRecyclerAdapter(this, getDataList())
    }

    private fun getDataList(): List<ChallengeModel> {
        val data = resources.getStringArray(R.array.cat_names).toList()
        val challengeModelList = mutableListOf<ChallengeModel>()
        data.forEachIndexed { index, item ->
            challengeModelList.add(
                ChallengeModel(
                    nameChallenge = item,
                    countChallenge = index.toString()
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
}