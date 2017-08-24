package com.example.vn008xw.carbeat.data.vo

import com.google.gson.annotations.SerializedName

data class SearchResult constructor(
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("results")
    val results: List<Movie>
)
