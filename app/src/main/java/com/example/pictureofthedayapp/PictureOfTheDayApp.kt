package com.example.pictureofthedayapp

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.DebugLogger
import com.example.pictureofthedayapp.repo.RemoteInjector

class PictureOfTheDayApp: Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(applicationContext)
            .okHttpClient(RemoteInjector.getOkHttpClient())
            .logger(DebugLogger())
            .build()
    }
}