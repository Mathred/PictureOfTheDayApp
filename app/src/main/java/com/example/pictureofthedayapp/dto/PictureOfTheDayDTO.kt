package com.example.pictureofthedayapp.dto

import com.example.pictureofthedayapp.viewdata.PictureOfTheDayViewData

data class PictureOfTheDayDTO(
    val title: String,
    val date: String,
    val url: String,
    val explanation: String
)

fun PictureOfTheDayDTO.toViewData() =
    PictureOfTheDayViewData(title, date, url, explanation)