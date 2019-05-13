package com.ak.be.engine.controller.user.dto

import com.ak.be.engine.controller.restaurant.dto.CompleteUserDto
import com.ak.be.engine.controller.restaurant.dto.RestaurantDto

data class AdminContextDto(val user: CompleteUserDto,
                           val restaurants: List<RestaurantDto>)
