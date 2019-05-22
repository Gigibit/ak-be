package com.ak.be.engine.service.user

import com.ak.be.engine.service.model.Restaurant
import com.ak.be.engine.service.model.User
import org.springframework.security.core.userdetails.UserDetails

interface UserService {
    fun createUserDetails(username: String?): UserDetails
    fun getRestaurantsByUserId(userId: Int): List<Restaurant>
    fun createUser(email: String, password: String, firstName: String, lastName: String): User
}
