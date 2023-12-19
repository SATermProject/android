package com.konkuk.sadelivery.remote.api

import com.konkuk.sadelivery.entity.Food
import com.konkuk.sadelivery.entity.OrderHistory
import com.konkuk.sadelivery.entity.Restaurant
import com.konkuk.sadelivery.remote.model.PostOrderRequest
import com.konkuk.sadelivery.remote.model.PostReviewRequest
import com.konkuk.sadelivery.remote.model.PostSearchRequest
import retrofit2.Response
import retrofit2.http.*

interface MainApi {

    @POST("search/saveSearchHistory")
    suspend fun postSearchHistory(
        @Body postSearchRequest: PostSearchRequest
    ): Response<Unit>

    @GET("search")
    suspend fun getSearchResult(
        @Query("keyword") keyword: String
    ): Response<List<Restaurant>>


    @POST("review/{orderId}")
    suspend fun postReview(
        @Path("orderId") orderId: Long,
        @Body postReviewRequest: PostReviewRequest
    ): Response<Unit>

    @GET("orderHistory/user/{userId}")
    suspend fun getOrderHistories(
        @Path("userId") userId: Long,
    ): Response<List<OrderHistory>>


    @POST("order")
    suspend fun postOrder(
        @Body postOrderRequest: PostOrderRequest
    ): Response<Unit>

    @GET("{restaurantId}/food")
    suspend fun getFood(
        @Path("restaurantId") restaurantId: Long
    ): Response<List<Food>>

}
