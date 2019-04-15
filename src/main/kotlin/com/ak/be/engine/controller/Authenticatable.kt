package com.ak.be.engine.controller

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

interface Authenticatable {

    fun getAuthentication(): Authentication? {
        return SecurityContextHolder.getContext().authentication
    }

}