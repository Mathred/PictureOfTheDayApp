package com.example.pictureofthedayapp.repo

import com.example.pictureofthedayapp.BuildConfig
import com.example.pictureofthedayapp.dto.PictureOfTheDayDTO
import com.example.pictureofthedayapp.utils.getTodayDate
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("/planetary/apod")
    suspend fun getPictureOfTheDay(
        @Query("api_key") apiKey: String = BuildConfig.NASA_API_KEY,
        @Query("date") date: String = getTodayDate()
    ) : PictureOfTheDayDTO
}