package com.ak.be.engine.service.auth.impl

import com.ak.be.engine.auth.UserDetailsDefault
import com.ak.be.engine.service.auth.AuthService
import com.ak.be.engine.service.model.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthServiceDefault : AuthService {
    override fun getUserOrFail(): User {
        val authentication = (SecurityContextHolder.getContext().authentication
                ?: throw IllegalStateException("authentication is null, probably user is not logged in"))

        val userDetails = authentication.principal as? UserDetailsDefault
                ?: throw IllegalStateException("authentication.principal is not of type UserDetailsDefault")
        return userDetails.user
    }
}