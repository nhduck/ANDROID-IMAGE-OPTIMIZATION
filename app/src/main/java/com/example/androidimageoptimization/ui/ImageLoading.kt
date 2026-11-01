package com.example.androidimageoptimization.ui

import android.widget.ImageView
import com.example.androidimageoptimization.R

/**
 * ĐIỂM CẮM DUY NHẤT để hiển thị ảnh trong toàn app.
 * Hiện tại: chỉ đặt placeholder, chưa tải ảnh thật.
 * TODO: thay phần thân hàm bằng Coil
 *   (tải bất đồng bộ, cache RAM + disk, resize, placeholder, ảnh lỗi, timeout).
 */
fun ImageView.loadImage(url: String) {
    setImageResource(R.drawable.ic_placeholder)
}