package com.ak.be.engine.service.model

import com.ak.be.engine.controller.restaurant.dto.RestaurantDto

data class Restaurant(val id: Int,
                      val title: String,
                      val imgUrl: String? = null,
                      val description: String? = null)

fun Restaurant.toDto(): RestaurantDto {
    return RestaurantDto(this.id, this.title, this.imgUrl, this.description)
}