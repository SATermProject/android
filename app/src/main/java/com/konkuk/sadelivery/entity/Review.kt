package com.konkuk.sadelivery.entity

data class Review(
    val id: Long,
    val rating: Int,
    val content: String,
    val restaurant: Restaurant
)
