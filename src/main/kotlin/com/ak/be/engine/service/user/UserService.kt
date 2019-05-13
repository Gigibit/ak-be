package com.ak.be.engine.service.user

import com.ak.be.engine.service.model.Restaurant
import org.springframework.security.core.userdetails.UserDetails

interface UserService {
    fun createUserDetails(username: String?): UserDetails
    fun getRestaurantsByUserId(userId: Int): List<Restaurant>
}
