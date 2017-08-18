package com.example.vn008xw.golf.vo

import com.google.gson.annotations.SerializedName

data class Item constructor(
    @SerializedName("itemId")
    val itemId: Int,
    @SerializedName("parentItemId")
    val parentItemId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("msrp")
    val msrp: Double,
    @SerializedName("salePrice")
    val salePrice: Double,
    @SerializedName("upc")
    val upc: String,
    @SerializedName("categoryPath")
    val categoryPath: String,
    @SerializedName("shortDescription")
    val shortDescription: String,
    @SerializedName("longDescription")
    val longDescription: String,
    @SerializedName("brandName")
    val brandName: String,
    @SerializedName("thumbnailImage")
    val thumbnail: String,
    @SerializedName("mediumImage")
    val mediumImage: String,
    @SerializedName("largeImage")
    val largeImage: String
)