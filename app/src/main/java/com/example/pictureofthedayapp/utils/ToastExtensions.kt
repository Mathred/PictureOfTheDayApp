package com.example.pictureofthedayapp.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Context.showTextToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.showTextToast(@StringRes textId: Int) {
    Toast.makeText(this, textId, Toast.LENGTH_SHORT).show()
}

fun Fragment.showTextToast(text: String) {
    Toast.makeText(this.context, text, Toast.LENGTH_LONG).show()
}