package com.ak.be.engine.controller.user.impl

import com.ak.be.engine.controller.restaurant.dto.CompleteUserDto
import com.ak.be.engine.controller.user.UserController
import com.ak.be.engine.controller.user.dto.*
import com.ak.be.engine.service.auth.AuthService
import com.ak.be.engine.service.model.toDto
import com.ak.be.engine.service.user.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController

@RestController
class UserControllerDefault(val authService: AuthService,
                            val userService: UserService) : UserController {

    override fun getUserById(userId: Int): GetUserByIdResponse {
        val userOrFail = authService.getUserOrFail()
        val authorities = userOrFail.authorities.map { it.toDto() }
        val completeUserDto = CompleteUserDto(userOrFail.toDto(), authorities)
        return GetUserByIdResponse(completeUserDto)

    }

    @PreAuthorize("hasAuthority('USER')")
    override fun getUserMe(): GetUserMeResponse {
        val userOrFail = authService.getUserOrFail()
        val authorities = userOrFail.authorities.map { it.toDto() }
        val completeUserDto = CompleteUserDto(userOrFail.toDto(), authorities)
        return GetUserMeResponse(UserContextDto(completeUserDto))
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    override fun getAdminMe(): GetAdminMeResponse {
        val userOrFail = authService.getUserOrFail()
        val restaurants = userService.getRestaurantsByUserId(userOrFail.id)
        val authorities = userOrFail.authorities.map { it.toDto() }
        val completeUserDto = CompleteUserDto(userOrFail.toDto(), authorities)
        val restaurantsDto = restaurants.map { it.toDto() }
        return GetAdminMeResponse(AdminContextDto(completeUserDto, restaurantsDto))
    }
}