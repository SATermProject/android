package com.konkuk.sadelivery.remote.api

import com.konkuk.sadelivery.entity.Restaurant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecommendApi {

    @GET("recommendation/review/{userId}")
    suspend fun getReviewRecommend(
        @Path("userId") userId: Long
    ): Response<List<Restaurant>>

    @GET("recommendation/order/{userId}")
    suspend fun getOrderRecommend(
        @Path("userId") userId: Long
    ): Response<List<Restaurant>>

    @GET("recommendation/rating/{userId}")
    suspend fun getRatingRecommend(
        @Path("userId") userId: Long
    ): Response<List<Restaurant>>

    @GET("recommendation/search/{userId}")
    suspend fun getSearchRecommend(
        @Path("userId") userId: Long
    ): Response<List<Restaurant>>


}