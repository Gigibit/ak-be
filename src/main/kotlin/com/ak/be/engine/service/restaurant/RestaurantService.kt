package com.ak.be.engine.service.restaurant

import com.ak.be.engine.service.restaurant.model.Restaurant

interface RestaurantService {
    fun getRestaurantById(id: Int): Restaurant?
}
