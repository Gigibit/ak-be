package com.ak.be.engine.service.model

import com.ak.be.engine.controller.restaurant.dto.RestaurantDto
import com.ak.be.engine.db.entity.AkRestaurantEntity

data class Restaurant(val id: Int,
                      val title: String,
                      val imgUrl: String? = null,
                      val description: String? = null) {
    companion object
}

fun Restaurant.toDto(): RestaurantDto {
    return RestaurantDto(this.id, this.title, this.imgUrl, this.description)
}

fun Restaurant.Companion.fromEntity(restaurantEntity: AkRestaurantEntity): Restaurant {
    return Restaurant(restaurantEntity.id, restaurantEntity.title, restaurantEntity.imgUrl, restaurantEntity.description)
}