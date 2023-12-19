package com.konkuk.sadelivery.entity

import com.google.gson.annotations.SerializedName

data class Food(
    @SerializedName("foodId")
    val id: Long,
    @SerializedName("foodName")
    val name: String,
    @SerializedName("categoryId")
    val categoryId: Long,
    @SerializedName("categoryName")
    val categoryName: String,
    @SerializedName("restaurantId")
    val restaurantId: Long,
    val restaurantName: String
)
