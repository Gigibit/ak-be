package com.ak.be.engine.service.model

import com.ak.be.engine.controller.dish.dto.DishDto

data class Dish(val id: Int, val title: String)

fun Dish.toDto(): DishDto {
    return DishDto(this.id, this.title)
}