package com.example.androidimageoptimization.ui

import android.widget.ImageView
import com.example.androidimageoptimization.R
import android.content.Context

/** Ảnh dùng chung cho cả hai nhánh baseline và optimized. */
object ImagePlaceholders {
    val loading = R.drawable.ic_placeholder   // đang tải
    val error = R.drawable.ic_error           // tải lỗi
}

/**
 * ĐIỂM CẮM DUY NHẤT để hiển thị ảnh trong toàn app.
 * Hiện tại: chỉ đặt placeholder, chưa tải ảnh thật.
 * TODO: thay phần thân hàm bằng Coil
 *   (tải bất đồng bộ, cache RAM + disk, resize, placeholder, ảnh lỗi, timeout).
 */
fun ImageView.loadImage(url: String) {
    setImageResource(R.drawable.ic_placeholder)
}

/**
 * ĐIỂM CẮM thứ hai: xóa toàn bộ cache ảnh (RAM + disk).
 * TODO: gọi imageLoader.memoryCache?.clear() và imageLoader.diskCache?.clear().
 */
fun clearImageCache(context: Context) {
    // chưa làm gì, chờ Coil
}