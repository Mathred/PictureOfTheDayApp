package com.example.pictureofthedayapp.ui.main.fragments

import androidx.fragment.app.Fragment

abstract class TaggedFragment: Fragment() {
    abstract fun getFragmentTag(): String
    abstract fun newInstance(): Fragment
}