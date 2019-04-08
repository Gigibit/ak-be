package com.ak.be.engine.service

import com.ak.be.engine.db.repository.DishRepository
import com.ak.be.engine.service.model.Dish
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DishService {

    @Autowired
    private lateinit var dishRepository: DishRepository

    fun getDishesByRestaurantId(restaurantId: Int): List<Dish> {
        return dishRepository.findDishesByRestaurantId(restaurantId).map {
            Dish(it.id, it.title)
        }
    }

}