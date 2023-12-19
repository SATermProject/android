package com.konkuk.sadelivery.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.konkuk.sadelivery.entity.Food
import com.konkuk.sadelivery.entity.OrderHistory
import com.konkuk.sadelivery.entity.Restaurant
import com.konkuk.sadelivery.presentation.main.adapter.FoodsAdapter
import com.konkuk.sadelivery.presentation.main.adapter.OrdersAdapter
import com.konkuk.sadelivery.presentation.main.adapter.RestaurantsAdapter
import com.konkuk.sadelivery.presentation.main.adapter.SearchAdapter

object BindingAdapters {

    @BindingAdapter("cornerCrop")
    @JvmStatic
    fun setClipToOutline(imageView: ImageView, clip: Boolean) {
        imageView.clipToOutline = clip
    }

    @BindingAdapter("restaurants")
    @JvmStatic
    fun setRestaurantList(recyclerView: RecyclerView, restaurants: List<Restaurant>?) {
        with(recyclerView.adapter as RestaurantsAdapter) {
            if (restaurants != null) {
                this.restaurants = restaurants
                notifyDataSetChanged()
            }
        }
    }

    @BindingAdapter("foods")
    @JvmStatic
    fun setFoodList(recyclerView: RecyclerView, foods: List<Food>?) {
        with(recyclerView.adapter as FoodsAdapter) {
            if (foods != null) {
                this.foods = foods
                this.deselect()
                notifyDataSetChanged()
            }
        }
    }

    @BindingAdapter("orders")
    @JvmStatic
    fun setOrdersList(recyclerView: RecyclerView, orders: List<OrderHistory>?) {
        with(recyclerView.adapter as OrdersAdapter) {
            if (orders != null) {
                this.orders = orders
                notifyDataSetChanged()
            }
        }
    }

    @BindingAdapter("search_results")
    @JvmStatic
    fun setSearchResult(recyclerView: RecyclerView, results: List<Restaurant>?) {
        with(recyclerView.adapter as SearchAdapter) {
            if (results != null) {
                this.restaurants = results
                notifyDataSetChanged()
            }
        }
    }
}