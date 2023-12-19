package com.konkuk.sadelivery.presentation.main

import com.konkuk.sadelivery.entity.Restaurant
import com.konkuk.sadelivery.presentation.base.BaseRepository
import com.konkuk.sadelivery.remote.api.MainApi
import com.konkuk.sadelivery.remote.api.RecommendApi
import com.konkuk.sadelivery.remote.model.PostOrderRequest
import com.konkuk.sadelivery.remote.model.PostReviewRequest
import com.konkuk.sadelivery.remote.model.PostSearchRequest
import com.konkuk.sadelivery.utils.RetrofitClient

class MainRepository : BaseRepository() {
    private val recApi = RetrofitClient.create(RecommendApi::class.java)
    private val api = RetrofitClient.create(MainApi::class.java)

    suspend fun postOrder(userId: Long, restaurantId: Long, foodId: Long) =
        makeRequest { api.postOrder(PostOrderRequest(userId, restaurantId, foodId)) }

    suspend fun postSearchHistory(restaurant: Restaurant) =
        makeRequest {
            api.postSearchHistory(
                PostSearchRequest(
                    restaurantId = restaurant.id,
                    restaurantName = restaurant.name,
                    categoryId = restaurant.categoryId,
                    categoryName = restaurant.categoryName
                )
            )
        }

    suspend fun getSearchResult(keyword: String) =
        makeRequest { api.getSearchResult(keyword) }

    suspend fun postReview(orderId: Long, id: Long, rating: Int, content: String) =
        makeRequest { api.postReview(orderId = orderId, PostReviewRequest(id, rating, content)) }

    suspend fun getOrderHistories(userId: Long) =
        makeRequest { api.getOrderHistories(userId) }

    suspend fun getFood(restaurantId: Long) =
        makeRequest { api.getFood(restaurantId) }


    suspend fun getReviewRecommend(userId: Long) =
        makeRequest { recApi.getReviewRecommend(userId) }

    suspend fun getOrderRecommend(userId: Long) =
        makeRequest { recApi.getOrderRecommend(userId) }

    suspend fun getRatingRecommend(userId: Long) =
        makeRequest { recApi.getRatingRecommend(userId) }

    suspend fun getSearchRecommend(userId: Long) =
        makeRequest { recApi.getSearchRecommend(userId) }


}