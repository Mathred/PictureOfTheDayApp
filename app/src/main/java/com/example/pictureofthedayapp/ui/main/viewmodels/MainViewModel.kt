package com.example.pictureofthedayapp.ui.main.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pictureofthedayapp.dto.toViewData
import com.example.pictureofthedayapp.repo.NasaRepositoryImpl
import com.example.pictureofthedayapp.viewdata.PictureOfTheDayViewData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val nasaRepo: NasaRepositoryImpl = NasaRepositoryImpl.getInstance()) : ViewModel() {

    private var _liveData = MutableLiveData<LoadState<PictureOfTheDayViewData>>()
    val liveData: LiveData<LoadState<PictureOfTheDayViewData>> get() = _liveData

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            _liveData.postValue(LoadState.Loading)
            Log.d(LOG_TAG, "_liveData.postValue(LoadState.Loading)")
            try {
                _liveData.postValue(LoadState.Success(nasaRepo.getPictureOfTheDay().toViewData()))
                Log.d(LOG_TAG, "_liveData.postValue(LoadState.Success)")
            } catch (e: Exception) {
                _liveData.postValue(LoadState.Error(e))
                Log.d(LOG_TAG, "_liveData.postValue(LoadState.Error) $e")
            }
        }
    }

    companion object {
        private const val LOG_TAG = "MainViewModel"
    }
}