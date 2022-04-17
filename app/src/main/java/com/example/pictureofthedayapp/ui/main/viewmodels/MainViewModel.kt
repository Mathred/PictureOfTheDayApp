package com.example.pictureofthedayapp.ui.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pictureofthedayapp.dto.toViewData
import com.example.pictureofthedayapp.repo.NasaRepositoryImpl
import com.example.pictureofthedayapp.ui.main.fragments.MainFragment
import com.example.pictureofthedayapp.utils.getDayBeforeYesterdayDate
import com.example.pictureofthedayapp.utils.getYesterdayDate
import com.example.pictureofthedayapp.viewdata.PictureOfTheDayViewData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val nasaRepo: NasaRepositoryImpl = NasaRepositoryImpl.getInstance()) : ViewModel() {

    private var _liveData = MutableLiveData<LoadState<PictureOfTheDayViewData>>()
    val liveData: LiveData<LoadState<PictureOfTheDayViewData>> get() = _liveData

    private var _photoDate: MainFragment.Companion.PhotoDate = MainFragment.Companion.PhotoDate.TODAY
        set(value) {
            if (field != value) {
                field = value
                getData()
            }
        }

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            _liveData.postValue(LoadState.Loading)
            try {
                val response = when (_photoDate) {
                    MainFragment.Companion.PhotoDate.TODAY -> nasaRepo.getPictureOfTheDay()
                    MainFragment.Companion.PhotoDate.YESTERDAY -> nasaRepo.getPictureByDate(getYesterdayDate())
                    MainFragment.Companion.PhotoDate.BEFORE_YESTERDAY -> nasaRepo.getPictureByDate(getDayBeforeYesterdayDate())
                }.toViewData()
                _liveData.postValue(LoadState.Success(response))
            } catch (e: Exception) {
                _liveData.postValue(LoadState.Error(e))
            }
        }
    }

    fun setPhotoDate(photoDate: MainFragment.Companion.PhotoDate) {
        _photoDate = photoDate
    }

    companion object {
        private const val LOG_TAG = "MainViewModel"
    }
}