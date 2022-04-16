package com.example.pictureofthedayapp.utils

import android.content.res.Resources

class ViewExtensions {
}

fun Int.pxToDp() = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()