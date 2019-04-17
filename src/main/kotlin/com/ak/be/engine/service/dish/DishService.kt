package com.ak.be.engine.service.dish

import com.ak.be.engine.service.model.Dish

interface DishService {
    fun getDishesByRestaurantId(restaurantId: Int): List<Dish>
    fun getDishById(id: Int): Dish?
}
