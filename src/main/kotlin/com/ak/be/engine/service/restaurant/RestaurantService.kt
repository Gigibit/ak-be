package com.ak.be.engine.service.restaurant

import com.ak.be.engine.service.model.Restaurant

interface RestaurantService {
    fun getRestaurantById(id: Int): Restaurant?
}
