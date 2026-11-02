package com.example.androidimageoptimization.model

data class Product (
    val id: Int,
    val name: String,
    val description: String,
    //cho ảnh nhỏ dùng cho danh sách
    val thumbUrl: String,
    //ảnh lớn dùng cho xem chi tiết
    val imageUrl: String,
)