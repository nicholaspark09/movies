package com.example.vn008xw.carbeat.data.vo

import com.google.gson.annotations.SerializedName

data class Poster constructor(
    @SerializedName("aspect_ratio")
    val aspectRatio: Float,
    @SerializedName("file_path")
    val filePath: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("iso_639_1")
    val iso: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("width")
    val width: Int
)