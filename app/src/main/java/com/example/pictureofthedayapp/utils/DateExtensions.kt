package com.example.pictureofthedayapp.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

fun getTodayDate(): String {
    dateFormat.timeZone = TimeZone.getTimeZone("America/Houston")
    return dateFormat.format(Calendar.getInstance().time)
}

fun getYesterdayDate(): String {
    val today = Calendar.getInstance()
    today.add(Calendar.DAY_OF_YEAR, -1)
    return dateFormat.format(today.time)
}

fun getDayBeforeYesterdayDate(): String {
    val today = Calendar.getInstance()
    today.add(Calendar.DAY_OF_YEAR, -2)
    return dateFormat.format(today.time)
}