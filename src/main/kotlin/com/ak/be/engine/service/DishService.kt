package com.ak.be.engine.service

import com.ak.be.engine.db.entity.AkDishEntity
import com.ak.be.engine.db.repository.DishRepository
import com.ak.be.engine.service.model.Dish
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class DishService {

    @Autowired
    private lateinit var dishRepository: DishRepository

    fun getDishesByRestaurantId(restaurantId: Int): List<Dish> {
        return dishRepository.findDishesByRestaurantId(restaurantId).map {
            Dish(it.id, it.title)
        }
    }

    fun getDishById(id: Int): Dish? {
        val findById: Optional<AkDishEntity> = dishRepository.findById(id)
        return if (findById.isPresent) {
            Dish(findById.get().id, findById.get().title)
        } else {
            null
        }
    }


}