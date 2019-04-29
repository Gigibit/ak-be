package com.ak.be.engine.service.model

import com.ak.be.engine.controller.restaurant.dto.UserDto
import java.io.Serializable

data class User(val id: Int,
                val email: String,
                val passwordHash: String,
                val firstName: String,
                val lastName: String,
                val authorities: List<Authority>,
                val accountExpired: Boolean,
                val accountLocked: Boolean,
                val credentialsExpired: Boolean,
                val enabled: Boolean) : Serializable

fun User.toDto(): UserDto {
    return UserDto(this.id, this.firstName, this.lastName)
}