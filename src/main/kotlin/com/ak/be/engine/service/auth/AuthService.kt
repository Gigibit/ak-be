package com.ak.be.engine.service.auth

import com.ak.be.engine.service.model.User

interface AuthService {
    fun getUserOrFail(): User
}
