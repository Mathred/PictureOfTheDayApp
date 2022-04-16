package com.example.pictureofthedayapp.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.annotation.ExperimentalCoilApi
import coil.load
import com.example.pictureofthedayapp.R
import com.example.pictureofthedayapp.ui.main.viewmodels.LoadState
import com.example.pictureofthedayapp.ui.main.viewmodels.MainViewModel
import com.example.pictureofthedayapp.utils.showTextToast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.progressindicator.CircularProgressIndicator

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private lateinit var progress: CircularProgressIndicator
    private lateinit var ivPicture: AppCompatImageView
    private lateinit var bottomSheet: ConstraintLayout
    private lateinit var bottomSheetTvDescriptionHeader: AppCompatTextView
    private lateinit var bottomSheetTvDescription: AppCompatTextView
    private lateinit var btnReload: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    @OptIn(ExperimentalCoilApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initObservers()
        viewModel.getData()
    }

    private fun initViews(view: View) {
        progress = view.findViewById(R.id.progress_circular)
        ivPicture = view.findViewById(R.id.iv_picture)
        bottomSheet = view.findViewById(R.id.bottom_sheet_container)
        bottomSheetTvDescriptionHeader =
            bottomSheet.findViewById(R.id.bottom_sheet_description_header)
        bottomSheetTvDescription = bottomSheet.findViewById(R.id.bottom_sheet_description)
        btnReload = view.findViewById(R.id.btn_reload)

        btnReload.setOnClickListener {
            viewModel.getData()
        }

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.skipCollapsed = true
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    @ExperimentalCoilApi
    private fun initObservers() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            when (it) {
                is LoadState.Error -> {
                    progress.isVisible = false
                    btnReload.isVisible = true
                    ivPicture.isVisible = false
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                    it.error.message?.let { errorMessage -> showTextToast(errorMessage) }
                }
                is LoadState.Loading -> {
                    progress.isVisible = true
                    btnReload.isVisible = false
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
                is LoadState.Success -> {
                    progress.isVisible = false
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
                        bottomSheetTvDescriptionHeader.text = this.title
                        bottomSheetTvDescription.text = this.explanation
                    }
                }
            }
        }
    }

}