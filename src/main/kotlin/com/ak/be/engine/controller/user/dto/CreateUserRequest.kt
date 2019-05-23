package com.ak.be.engine.controller.user.dto

import com.ak.be.engine.controller.core.dto.BaseRequest
import com.ak.be.engine.controller.restaurant.dto.CreateRestaurantDto
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class CreateUserRequest(@Email val email: String,
                             @NotEmpty val firstName: String,
                             @NotEmpty val lastName: String,
                             @NotEmpty val password: String,
                             val restaurant: CreateRestaurantDto? = null) : BaseRequest()
