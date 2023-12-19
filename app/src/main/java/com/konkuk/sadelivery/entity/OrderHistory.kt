package com.konkuk.sadelivery.entity

data class OrderHistory(
    val id: Long,
    val restaurant: Restaurant,
    val food: Food,
    val review: Review?
)