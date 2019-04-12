package com.ak.be.engine.controller.dish

import com.ak.be.engine.controller.dish.dto.DishDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

const val GET_DISH_BY_ID = "/dishes/{id}"

interface DishController {
    @GetMapping(GET_DISH_BY_ID)
    fun getDishById(@PathVariable id: Int): DishDto
}
