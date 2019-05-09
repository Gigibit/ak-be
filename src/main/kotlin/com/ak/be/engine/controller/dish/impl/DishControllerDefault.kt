package com.ak.be.engine.controller.dish.impl

import com.ak.be.engine.controller.dish.DishController
import com.ak.be.engine.controller.dish.dto.DishDto
import com.ak.be.engine.service.dish.DishService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class DishControllerDefault(val dishService: DishService) : DishController {

    override fun getDishById(@PathVariable id: Int): DishDto {
        val found = dishService.getDishById(id)?.let { DishDto(it.id, it.title) }
        if (found == null) {
            throw IllegalArgumentException("not found")
        } else {
            return DishDto(found.id, found.title)
        }
    }
}