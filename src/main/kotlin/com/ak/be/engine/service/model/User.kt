package com.ak.be.engine.service.model

import com.ak.be.engine.controller.restaurant.dto.UserDto

data class User(val id: Int, val email: String, val firstName: String, val lastName: String)

fun User.toDto(): UserDto {
    return UserDto(this.id, this.firstName, this.lastName)
}