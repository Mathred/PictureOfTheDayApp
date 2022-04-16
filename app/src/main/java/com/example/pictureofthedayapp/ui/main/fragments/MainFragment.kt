package com.example.pictureofthedayapp.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import coil.annotation.ExperimentalCoilApi
import coil.load
import com.example.pictureofthedayapp.databinding.MainFragmentBinding
import com.example.pictureofthedayapp.ui.main.viewmodels.LoadState
import com.example.pictureofthedayapp.ui.main.viewmodels.MainViewModel
import com.example.pictureofthedayapp.utils.showTextToast
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainFragment : TaggedFragment() {

    companion object {
        fun newInstance() = MainFragment()
        const val FRAGMENT_TAG = "MainFragmentTag"
    }

    override fun getFragmentTag() = FRAGMENT_TAG
    override fun newInstance() = MainFragment()

    private lateinit var viewModel: MainViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @OptIn(ExperimentalCoilApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = ViewModelProvider(this@MainFragment)[MainViewModel::class.java]
            initViews()
            initObservers()
            viewModel.getData()
        }
        return binding.root
    }

    @OptIn(ExperimentalCoilApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun MainFragmentBinding.initViews() {
        btnReload.setOnClickListener {
            viewModel.getData()
        }

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetDialog.root)
        bottomSheetBehavior.skipCollapsed = true
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    @ExperimentalCoilApi
    private fun MainFragmentBinding.initObservers() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            when (it) {
                is LoadState.Error -> {
                    progressCircular.isVisible = false
                    btnReload.isVisible = true
                    ivPicture.isVisible = false
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                    it.error.message?.let { errorMessage -> showTextToast(errorMessage) }
                }
                is LoadState.Loading -> {
                    progressCircular.isVisible = true
                    btnReload.isVisible = false
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
                is LoadState.Success -> {
                    progressCircular.isVisible = false
                    btnReload.isVisible = false
                    with(it.data) {
                        ivPicture.load(this.url) {
                            listener(
                                onSuccess = { _, _ ->
                                    ivPicture.isVisible = true
                                },
                                onError = { _, _ ->
                                    ivPicture.isVisible = false
                                }
                            )
                        }

                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                        bottomSheetDialog.bottomSheetDescriptionHeader.text = this.title
                        bottomSheetDialog.bottomSheetDescription.text = this.explanation
                    }
                }
            }
        }
    }

}