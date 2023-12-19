package com.konkuk.sadelivery.remote.model

data class PostOrderRequest(
    val userId: Long,
    val restaurantId: Long,
    val foodId: Long
)
