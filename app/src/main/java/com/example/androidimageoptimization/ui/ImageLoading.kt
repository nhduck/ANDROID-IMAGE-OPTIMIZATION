package com.example.androidimageoptimization.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.example.androidimageoptimization.R
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

private val imageExecutor = Executors.newFixedThreadPool(4)

object ImagePlaceholders {
    val loading = R.drawable.ic_placeholder
    val error = R.drawable.ic_error
}

fun ImageView.loadImage(imageUrl: String) {
    tag = imageUrl
    setImageResource(ImagePlaceholders.loading)

    imageExecutor.execute {
        var connection: HttpURLConnection? = null

        val imageBitmap = try {
            connection = URL(imageUrl).openConnection() as HttpURLConnection
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.useCaches = false

            connection.inputStream.use { input ->
                BitmapFactory.decodeStream(input)
            }
        } catch (exception: Exception) {
            null
        } finally {
            connection?.disconnect()
        }

        post {
            if (tag == imageUrl) {
                if (imageBitmap != null) {
                    setImageBitmap(imageBitmap)
                } else {
                    setImageResource(ImagePlaceholders.error)
                }
            }
        }
    }
}

fun clearImageCache(context: Context) {
    // co so chua luu cache
}