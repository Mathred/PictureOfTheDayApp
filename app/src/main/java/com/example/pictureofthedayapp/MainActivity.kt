package com.example.pictureofthedayapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pictureofthedayapp.databinding.MainActivityBinding
import com.example.pictureofthedayapp.ui.main.fragments.MainFragment
import com.example.pictureofthedayapp.utils.Navigator

class MainActivity : AppCompatActivity() {

    private var _binding: MainActivityBinding? = null
    private val binding: MainActivityBinding get() = _binding!!
    private var navigator = Navigator(this)

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = MainActivityBinding.inflate(layoutInflater).apply {
            setupNav()
        }
        setContentView(binding.root)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance(), MainFragment.FRAGMENT_TAG)
                .commitNow()
        }
    }

    private fun MainActivityBinding.setupNav() {
        bottomNav.setOnItemSelectedListener {
            if (it.itemId == bottomNav.selectedItemId) {
                return@setOnItemSelectedListener false
            } else {
                when (it.itemId) {
                    R.id.picture_of_the_day -> navigator.openPictureFragment()
                    R.id.wikipedia -> navigator.openWikipediaSearch()
                }
                it.isChecked = true
                return@setOnItemSelectedListener true
            }
        }
    }

}