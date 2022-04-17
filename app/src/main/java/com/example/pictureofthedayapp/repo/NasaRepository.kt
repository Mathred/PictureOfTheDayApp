package com.example.pictureofthedayapp.repo

import com.example.pictureofthedayapp.dto.PictureOfTheDayDTO

interface NasaRepository {

    suspend fun getPictureOfTheDay(): PictureOfTheDayDTO
    suspend fun getPictureByDate(date: String): PictureOfTheDayDTO
}