package com.konkuk.sadelivery.remote.model

import com.konkuk.sadelivery.entity.Restaurant

data class RestaurantsResponse(
    val restaurants: List<Restaurant>
)