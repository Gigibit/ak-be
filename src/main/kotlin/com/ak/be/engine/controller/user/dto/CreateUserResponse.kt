package com.ak.be.engine.controller.user.dto

import com.ak.be.engine.controller.core.dto.BaseResponse
import com.ak.be.engine.controller.restaurant.dto.RestaurantDto
import com.ak.be.engine.controller.restaurant.dto.UserDto

class CreateUserResponse(val user: UserDto, val restaurant: RestaurantDto? = null) : BaseResponse()
