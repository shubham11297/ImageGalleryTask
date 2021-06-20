package com.example.imagegallerytask.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ImageTable")
data class ImagesDataItem(
    @PrimaryKey
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val download_url: String
)