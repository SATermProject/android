package com.konkuk.sadelivery.entity

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("restaurantId")
    val id: Long,
    @SerializedName("restaurantName")
    val name: String,
    @SerializedName("categoryId")
    val categoryId: Long,
    @SerializedName("categoryName")
    val categoryName: String
)