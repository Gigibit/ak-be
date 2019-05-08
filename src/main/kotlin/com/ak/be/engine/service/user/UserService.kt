package com.ak.be.engine.service.user

import org.springframework.security.core.userdetails.UserDetails

interface UserService {
    fun createUserDetails(username: String?): UserDetails
}
