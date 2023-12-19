package com.konkuk.sadelivery.remote.model

data class PostReviewRequest(
    val id: Long,
    val rating: Int,
    val content: String
)
