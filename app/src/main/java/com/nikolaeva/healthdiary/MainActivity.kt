package com.nikolaeva.healthdiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.nikolaeva.healthdiary.model.ChallengeModel

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

    override fun goToListsFragment() {
        replaceFragment(ListsFragment.newInstance(), "list")
    }

    override fun gotToAuthActivity() {
        startActivity(Intent(this, GoogleSignInActivity::class.java))
    }

    override fun goToPlusFragment() {
        replaceFragment(PlusFragment.newInstance(), "plus")
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
    fun goToListsFragment()
    fun gotToAuthActivity()
    fun goToPlusFragment()
}