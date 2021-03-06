package com.ak.be.engine.controller.user

import com.ak.be.engine.controller.user.dto.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

const val GET_USER_BY_ID = "/users/{userId}"
const val GET_USER_ME = "/users/me"
const val GET_ADMIN_ME = "/admins/me"
const val CREATE_USER = "/public/users"

interface UserController {
    @GetMapping(GET_USER_BY_ID)
    fun getUserById(@PathVariable userId: Int): GetUserByIdResponse

    @GetMapping(GET_USER_ME)
    fun getUserMe(): GetUserMeResponse

    @GetMapping(GET_ADMIN_ME)
    fun getAdminMe(): GetAdminMeResponse

    @PostMapping(CREATE_USER)
    fun createUser(@Valid @RequestBody request: CreateUserRequest): CreateUserResponse
}
