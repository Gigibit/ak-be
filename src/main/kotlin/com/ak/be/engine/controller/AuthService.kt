package com.ak.be.engine.controller

import com.ak.be.engine.service.model.User

interface AuthService {
    fun getUserOrFail(): User
}
