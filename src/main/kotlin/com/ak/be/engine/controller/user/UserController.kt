package com.ak.be.engine.controller.user

import com.ak.be.engine.controller.user.dto.GetAdminMeResponse
import com.ak.be.engine.controller.user.dto.GetUserByIdResponse
import com.ak.be.engine.controller.user.dto.GetUserMeResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

const val GET_USER_BY_ID = "/users/{userId}"
const val GET_USER_ME = "/users/me"
const val GET_ADMIN_ME = "/admins/me"

interface UserController {
    @GetMapping(GET_USER_BY_ID)
    fun getUserById(@PathVariable userId: Int): GetUserByIdResponse

    @GetMapping(GET_USER_ME)
    fun getUserMe(): GetUserMeResponse

    @GetMapping(GET_ADMIN_ME)
    fun getAdminMe(): GetAdminMeResponse
}
