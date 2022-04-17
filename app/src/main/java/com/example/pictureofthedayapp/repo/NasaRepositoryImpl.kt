package com.example.pictureofthedayapp.repo

import com.example.pictureofthedayapp.dto.PictureOfTheDayDTO

class NasaRepositoryImpl(private val nasaApi: NasaApi = RemoteInjector.injectNasaApi()): NasaRepository {

    override suspend fun getPictureOfTheDay(): PictureOfTheDayDTO {
        return nasaApi.getPictureOfTheDay()
    }

    override suspend fun getPictureByDate(date: String): PictureOfTheDayDTO {
        return nasaApi.getPictureOfTheDay(date = date)
    }

    companion object {
        fun getInstance() = NasaRepositoryImpl()
    }

}