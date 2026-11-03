package com.example.androidimageoptimization.ui

import android.content.Context
import android.widget.ImageView
import com.example.androidimageoptimization.R
import coil3.load
import coil3.request.CachePolicy
import coil3.SingletonImageLoader
import coil3.request.placeholder
import coil3.request.error
import android.util.Log
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import coil3.request.SuccessResult


object ImagePlaceholders {
    val loading = R.drawable.ic_placeholder
    val error = R.drawable.ic_error
}

fun ImageView.loadImage(imageUrl: String) {
    val start = System.nanoTime()
    load(imageUrl) {
        placeholder(ImagePlaceholders.loading)
        error(ImagePlaceholders.error)
        memoryCachePolicy(CachePolicy.ENABLED)
        diskCachePolicy(CachePolicy.ENABLED)
        listener(object : ImageRequest.Listener {
            override fun onSuccess(request: ImageRequest, result: SuccessResult) {
                val ms = (System.nanoTime() - start) / 1_000_000
                Log.d("IMG", "OK source=${result.dataSource} ms=$ms url=$imageUrl")
            }
            override fun onError(request: ImageRequest, result: ErrorResult) {
                val ms = (System.nanoTime() - start) / 1_000_000
                Log.d("IMG", "ERR ms=$ms url=$imageUrl")
            }
            override fun onCancel(request: ImageRequest) {
                val ms = (System.nanoTime() - start) / 1_000_000
                Log.d("IMG", "CANCEL ms=$ms url=$imageUrl")
            }
        })
    }
}

fun clearImageCache(context: Context) {
    val imageLoader = SingletonImageLoader.get(context)

    imageLoader.memoryCache?.clear()
    imageLoader.diskCache?.clear()
}