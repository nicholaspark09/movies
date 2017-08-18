package com.example.vn008xw.golf.vo

import com.google.gson.annotations.SerializedName

data class ItemsResult constructor(
    @SerializedName("items")
    val items: List<Item>
)
