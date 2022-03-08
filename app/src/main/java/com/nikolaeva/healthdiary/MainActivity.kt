package com.nikolaeva.healthdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), INavigationFragment {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goToMainFragment()
    }

    override fun goToMainFragment() {
        replaceFragment(MainScreenFragment.newInstance())
    }

    override fun goToProfileFragment() {
        replaceFragment(ProfileFragment.newInstance())
    }

    override fun goToChallengesFragment() {
        replaceFragment(ChallengesFragment.newInstance())
    }

    override fun goToListsFragment() {

        replaceFragment(ListsFragment.newInstance())
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0){
            goToMainFragment()
        }
    }
}

interface INavigationFragment {
    fun goToMainFragment()
    fun goToProfileFragment()
    fun goToChallengesFragment()
    fun goToListsFragment()
}