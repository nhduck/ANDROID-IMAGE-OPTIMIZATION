package com.example.androidimageoptimization.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.os.SystemClock
import android.util.Log
import android.widget.ImageView
import com.example.androidimageoptimization.R
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

private val imageExecutor = Executors.newFixedThreadPool(4)

private const val LOG_TAG = "IMG"
private const val BATCH_GAP_MS = 1500L

// Mốc thời gian chung của một đợt tải (dùng để tính ms trong log)
@Volatile private var batchStartMs = 0L
@Volatile private var lastRequestMs = 0L

private fun markRequest(): Long {
    val now = SystemClock.elapsedRealtime()
    if (batchStartMs == 0L || now - lastRequestMs > BATCH_GAP_MS) {
        batchStartMs = now          // đợt tải mới -> đặt lại mốc
    }
    lastRequestMs = now
    return batchStartMs
}

object ImagePlaceholders {
    val loading = R.drawable.ic_placeholder
    val error = R.drawable.ic_error
}

fun ImageView.loadImage(imageUrl: String) {
    tag = imageUrl
    setImageResource(ImagePlaceholders.loading)

    val batchStart = markRequest()

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

        val elapsed = SystemClock.elapsedRealtime() - batchStart
        if (imageBitmap != null) {
            Log.d(LOG_TAG, "OK source=NETWORK ms=$elapsed url=$imageUrl")
        } else {
            Log.d(LOG_TAG, "FAIL source=NETWORK_ERR ms=$elapsed url=$imageUrl")
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