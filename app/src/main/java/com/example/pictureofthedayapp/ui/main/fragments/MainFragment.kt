package com.example.pictureofthedayapp.ui.main.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import coil.load
import com.example.pictureofthedayapp.R
import com.example.pictureofthedayapp.ui.main.viewmodels.LoadState
import com.example.pictureofthedayapp.ui.main.viewmodels.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers(view)
        viewModel.getData()
    }

    private fun initObservers(view: View) {
        viewModel.liveData.observe(viewLifecycleOwner) {
            when (it) {
                is LoadState.Error -> {}
                is LoadState.Loading -> {}
                is LoadState.Success -> {
                    with(it.data) {
                        val ivPicture = view.findViewById<AppCompatImageView>(R.id.iv_picture)
                        ivPicture.load(this.url)
                    }
                }
            }
        }
    }

}