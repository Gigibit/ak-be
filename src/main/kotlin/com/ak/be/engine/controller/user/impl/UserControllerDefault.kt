package com.ak.be.engine.controller.user.impl

import com.ak.be.engine.controller.restaurant.dto.CompleteUserDto
import com.ak.be.engine.controller.user.UserController
import com.ak.be.engine.controller.user.dto.*
import com.ak.be.engine.service.auth.AuthService
import com.ak.be.engine.service.model.User
import com.ak.be.engine.service.model.toDto
import com.ak.be.engine.service.restaurant.RestaurantService
import com.ak.be.engine.service.user.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class UserControllerDefault(val authService: AuthService,
                            val userService: UserService, val restaurantService: RestaurantService) : UserController {

    @Transactional
    override fun createUser(@Valid @RequestBody request: CreateUserRequest): CreateUserResponse {
        val user: User = userService.createUser(
                request.email,
                request.password,
                request.firstName,
                request.lastName)

        return request.restaurant?.let {
            val createRestaurant = restaurantService.createRestaurant(user, it.title, it.imgUrl, it.description)
            CreateUserResponse(user.toDto(), createRestaurant.toDto())
        } ?: run {
            CreateUserResponse(user.toDto())
        }
    }

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