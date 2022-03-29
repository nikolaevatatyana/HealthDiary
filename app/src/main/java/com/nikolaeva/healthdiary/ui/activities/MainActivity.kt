package com.nikolaeva.healthdiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.nikolaeva.healthdiary.model.ChallengeModel
import com.nikolaeva.healthdiary.model.CheckListModel
import com.nikolaeva.healthdiary.ui.fragments.ChallengesFragment
import com.nikolaeva.healthdiary.ui.activities.GoogleSignInActivity
import com.nikolaeva.healthdiary.ui.fragments.DetailChallengeFragment
import com.nikolaeva.healthdiary.ui.fragments.DetailListFragment
import com.nikolaeva.healthdiary.ui.fragments.ListsFragment
import com.nikolaeva.healthdiary.ui.fragments.MainScreenFragment
import com.nikolaeva.healthdiary.ui.fragments.PlusFragment
import com.nikolaeva.healthdiary.ui.fragments.ProfileFragment

class MainActivity : AppCompatActivity(), INavigationFragment {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goToMainFragment()
    }

    override fun goToMainFragment() {
        replaceFragment(MainScreenFragment.newInstance(), "main")
    }

    override fun goToProfileFragment() {
        replaceFragment(ProfileFragment.newInstance(), "profile")
    }

    override fun goToChallengesFragment() {
        replaceFragment(ChallengesFragment.newInstance(), "challenge")
    }

    override fun goToDetailChallengeFragment(challengeModel: ChallengeModel) {
        addFragment(DetailChallengeFragment.newInstance(challengeModel), "detail")
    }

    override fun goToDetailListFragment(checkListModel: CheckListModel) {
        addFragment(DetailListFragment.newInstance(checkListModel), "detail")
    }

    override fun goToListsFragment() {
        replaceFragment(ListsFragment.newInstance(), "list")
    }

    override fun gotToPlusFragment() {
        replaceFragment(PlusFragment.newInstance(), "plus")
    }

    override fun gotToAuthActivity() {
        startActivity(Intent(this, GoogleSignInActivity::class.java))
    }

    override fun goToPlusFragment() {
        TODO("Not yet implemented")  //зачем ему вторая?
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, tag)
            .commit()
    }

    private fun addFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            goToMainFragment()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}

interface INavigationFragment {
    fun goToMainFragment()
    fun goToProfileFragment()
    fun goToChallengesFragment()
    fun goToDetailChallengeFragment(challengeModel: ChallengeModel)
    fun goToDetailListFragment(checkListModel: CheckListModel)
    fun goToListsFragment()
    fun gotToPlusFragment()
    fun gotToAuthActivity()
    fun goToPlusFragment()
}