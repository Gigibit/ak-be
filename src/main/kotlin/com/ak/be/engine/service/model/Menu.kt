package com.ak.be.engine.service.model

import com.ak.be.engine.controller.restaurant.dto.MenuDto
import com.ak.be.engine.db.entity.AkDishEntity
import com.ak.be.engine.db.entity.AkMenuEntity

data class Menu(val id: Int, val state: Int, val type: Int, val dish: Dish) {
    companion object
}

fun Menu.toDto(): MenuDto {
    return MenuDto(this.id, this.dish.toDto())
}

fun Menu.Companion.fromEntity(menuEntity: AkMenuEntity): Menu {
    val dish = menuEntity.dish?.let { d: AkDishEntity ->
        Dish(d.id, d.title)
    } ?: throw IllegalStateException("Dish is null")
    return Menu(menuEntity.id, menuEntity.state, menuEntity.type, dish)
}
