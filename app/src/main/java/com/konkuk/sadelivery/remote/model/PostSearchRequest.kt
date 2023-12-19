package com.konkuk.sadelivery.remote.model

import android.provider.ContactsContract.CommonDataKinds.Nickname

data class PostSearchRequest(
    val restaurantId: Long,
    val restaurantName: String,
    val categoryId: Long,
    val categoryName: String,
    val user: User = User()
)

data class User(
    val id: Long = 1,
    val nickname: String = "eleb"
)