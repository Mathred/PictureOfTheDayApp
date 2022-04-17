package com.example.pictureofthedayapp.utils

import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

class ViewExtensions {
}

fun Int.pxToDp() = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()

@OptIn(ExperimentalCoroutinesApi::class)
fun EditText.textChangesFlow(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySend(s)
            }
        }
        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}