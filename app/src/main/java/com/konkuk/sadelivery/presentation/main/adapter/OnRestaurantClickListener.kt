package com.konkuk.sadelivery.presentation.main.adapter

import com.konkuk.sadelivery.entity.Restaurant

interface OnRestaurantClickListener {
    fun onClicked(restaurant: Restaurant)
}