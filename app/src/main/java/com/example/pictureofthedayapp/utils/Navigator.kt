package com.example.pictureofthedayapp.utils

import androidx.appcompat.app.AppCompatActivity
import com.example.pictureofthedayapp.R
import com.example.pictureofthedayapp.ui.main.fragments.MainFragment
import com.example.pictureofthedayapp.ui.main.fragments.TaggedFragment
import com.example.pictureofthedayapp.ui.main.fragments.WikipediaSearchFragment

class Navigator(private val activity: AppCompatActivity) {

    private var activeFragment: TaggedFragment = MainFragment()

    private fun showFragment(fragment: TaggedFragment) {
        val fm = activity.supportFragmentManager
        val ft = fm.beginTransaction()

        fm.findFragmentByTag(fragment.getFragmentTag())?.let {
            ft.hide(activeFragment)
            ft.show(it)
            (it as? TaggedFragment)?.let { taggedFragment -> activeFragment = taggedFragment }
        } ?: run {
            ft.add(R.id.container, fragment, fragment.getFragmentTag())
            ft.addToBackStack(fragment.getFragmentTag())
            activeFragment = fragment
        }

        ft.commit()
    }


    fun openPictureFragment() {
        showFragment(MainFragment())
    }

    fun openWikipediaSearch() {
        showFragment(WikipediaSearchFragment())
    }

}