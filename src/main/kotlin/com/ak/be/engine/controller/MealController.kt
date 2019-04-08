package com.ak.be.engine.controller

import com.ak.be.engine.controller.dto.DishDto
import com.ak.be.engine.controller.dto.GetDishesByRestaurantIdResponse
import com.ak.be.engine.service.DishService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MealController {

    @Autowired
    lateinit var dishService: DishService

    @GetMapping("restaurant/{restaurantId}/dishes")
    fun getDishesByRestaurantId(@PathVariable restaurantId: Int): GetDishesByRestaurantIdResponse {
        val list = dishService.getDishesByRestaurantId(restaurantId).map { DishDto(it.id, it.title) }
        return GetDishesByRestaurantIdResponse(list)
    }
}