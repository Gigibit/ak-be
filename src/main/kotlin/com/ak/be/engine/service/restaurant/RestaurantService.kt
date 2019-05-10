package com.ak.be.engine.service.restaurant

import com.ak.be.engine.service.model.Restaurant
import com.ak.be.engine.service.model.User

interface RestaurantService {
    fun getRestaurantById(id: Int): Restaurant?
    fun userBelongsToRestaurant(user: User, restaurantId: Int): Boolean
    fun validateUserBelongsToRestaurant(user: User, restaurantId: Int)
}
