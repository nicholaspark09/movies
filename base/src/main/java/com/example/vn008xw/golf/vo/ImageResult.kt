package com.example.vn008xw.carbeat.data.vo

import com.google.gson.annotations.SerializedName

data class ImageResult constructor(
    @SerializedName("id")
    val id: Int,
    @SerializedName("backdrops")
    val backdrops: List<Poster>,
    @SerializedName("posters")
    val posters: List<Poster>
)