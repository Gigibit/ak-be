package com.ak.be.engine.service.model

import com.ak.be.engine.controller.restaurant.dto.MenuDto

data class Menu(val id: Int, val state: Int, val type: Int, val dish: Dish)

fun Menu.toDto(): MenuDto {
    return MenuDto(this.id, this.dish.toDto())
}
