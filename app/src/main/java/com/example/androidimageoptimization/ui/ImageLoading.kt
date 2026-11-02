package com.example.androidimageoptimization.ui

import android.content.Context
import android.widget.ImageView
import com.example.androidimageoptimization.R
import coil3.load
import coil3.request.CachePolicy
import coil3.SingletonImageLoader
import coil3.request.placeholder
import coil3.request.error


object ImagePlaceholders {
    val loading = R.drawable.ic_placeholder
    val error = R.drawable.ic_error
}

fun ImageView.loadImage(imageUrl: String) {
    load(imageUrl) {
        placeholder(R.drawable.ic_placeholder)
        error(R.drawable.ic_error)

        memoryCachePolicy(CachePolicy.ENABLED)
        diskCachePolicy(CachePolicy.ENABLED)
    }
}

fun clearImageCache(context: Context) {
    val imageLoader = SingletonImageLoader.get(context)

    imageLoader.memoryCache?.clear()
    imageLoader.diskCache?.clear()
}