package com.konkuk.sadelivery.presentation.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.sadelivery.entity.Food
import com.konkuk.sadelivery.entity.OrderHistory
import com.konkuk.sadelivery.entity.Restaurant
import com.konkuk.sadelivery.utils.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.reflect.KSuspendFunction1

enum class SortType {
    DEFAULT, SEARCH, ORDER, REVIEW, RATING
}

class MainViewModel(val repository: MainRepository) : ViewModel() {

    private val _pageFlow = MutableStateFlow(MainPage.HOME)
    val pageFlow = _pageFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, MainPage.HOME)

    fun goto(page: MainPage) {
        viewModelScope.launch {
            if (page == MainPage.HOME) requestRestaurantList()
            _pageFlow.emit(page)
        }
    }

    val restaurants = MutableLiveData<List<Restaurant>>(listOf())
    var recentSortType: SortType = SortType.RATING

    fun requestRestaurantList(sort: SortType = recentSortType, userId: Long = 1) {
        val function = when (sort) {
            SortType.DEFAULT -> repository::getRatingRecommend
            SortType.ORDER -> repository::getOrderRecommend
            SortType.SEARCH -> repository::getSearchRecommend
            SortType.REVIEW -> repository::getReviewRecommend
            SortType.RATING -> repository::getRatingRecommend
        }
        requestRec(function, userId)
        this.recentSortType = sort
    }

    private fun requestRec(
        repositoryFunction: KSuspendFunction1<Long, ApiState<List<Restaurant>>>,
        userId: Long
    ) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repositoryFunction(userId)
            }
            response.byState(
                onSuccess = {
                    restaurants.value = it
                }
            )
        }
    }

    var clickedRestaurant = MutableLiveData<Restaurant>()

    fun onRestaurantClicked(restaurant: Restaurant) {
        //foodlist요청하기
        clickedRestaurant.value = restaurant
        requestFoods(restaurant.id)
        goto(MainPage.Detail)
    }

    val foods = MutableLiveData<List<Food>>()

    private fun requestFoods(restaurantId: Long) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.getFood(restaurantId)
            }
            response.byState(
                onSuccess = {
                    foods.value = it
                }
            )
        }
    }

    val postOrderResponse = MutableLiveData<Food>()

    fun postOrder(selectedFood: Food?) {
        selectedFood?.let { food ->
            clickedRestaurant.value?.let { restaurant ->
                viewModelScope.launch {
                    val response = withContext(Dispatchers.IO) {
                        repository.postOrder(1, restaurant.id, food.id)

                    }
                    response.byState(
                        onSuccess = {
                            postOrderResponse.value = food
                            requestFoods(restaurant.id)
                            requestRestaurantList(recentSortType)
                        }
                    )
                }
            }
        }
    }

    val orderHistories = MutableLiveData<List<OrderHistory>>()

    fun requestOrderHistories() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.getOrderHistories(1)
            }
            response.byState(
                onSuccess = {
                    orderHistories.value = it
                }
            )
        }
    }

    val searchResult = MutableLiveData<List<Restaurant>>()

    fun searchKeyword(keyword: String) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.getSearchResult(keyword)
            }
            response.byState(
                onSuccess = {
                    searchResult.value = it
                }
            )
        }
    }

    fun onRestaurantSearched(restaurant: Restaurant) {
        clickedRestaurant.value = restaurant
        requestFoods(restaurant.id)
        goto(MainPage.Detail)
        postSearch(restaurant)
    }


    private fun postSearch(restaurant: Restaurant) {
        Log.d("DEBUG", "$restaurant")
        viewModelScope.launch(Dispatchers.IO) {
            repository.postSearchHistory(restaurant)
        }
    }

    val reviewResult = MutableLiveData<Unit>()

    fun postReview(orderId: Long, id: Long, rating: Int, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.postReview(orderId, id, rating, content)
        }
    }

}