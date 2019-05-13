package com.ak.be.engine.controller.restaurant.dto

import com.ak.be.engine.controller.user.dto.AuthorityDto

data class UserDto(val id: Int, val firstName: String, val lastName: String)
data class CompleteUserDto(val user: UserDto, val authorities: List<AuthorityDto>)