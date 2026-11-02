package com.example.androidimageoptimization.ui

import android.content.Context
import android.widget.ImageView
import com.example.androidimageoptimization.R
import coil3.load
import coil3.request.CachePolicy
import coil3.SingletonImageLoader
object ImagePlaceholders {
    val loading = R.drawable.ic_placeholder
    val error = R.drawable.ic_error
}

fun ImageView.loadImage(imageUrl: String) {
    setImageResource(ImagePlaceholders.loading)

    load(imageUrl) {
        memoryCachePolicy(CachePolicy.ENABLED)
        diskCachePolicy(CachePolicy.ENABLED)
    }
}

fun clearImageCache(context: Context) {
    val imageLoader = SingletonImageLoader.get(context)

    imageLoader.memoryCache?.clear()
    imageLoader.diskCache?.clear()
}