package com.ak.be.engine.service.restaurant.impl

import com.ak.be.engine.db.entity.AkRestaurantEntity
import com.ak.be.engine.db.repository.RestaurantRepository
import com.ak.be.engine.db.repository.UserRepository
import com.ak.be.engine.ext.toKotlinOptionalOrFail
import com.ak.be.engine.service.model.Restaurant
import com.ak.be.engine.service.model.User
import com.ak.be.engine.service.model.fromEntity
import com.ak.be.engine.service.restaurant.RestaurantService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RestaurantServiceDefault(val restaurantRepository: RestaurantRepository, val userRepository: UserRepository) : RestaurantService {

    override fun getUserRestaurants(user: User): List<Restaurant> {
        val akUserEntity = userRepository.findById(user.id).toKotlinOptionalOrFail()
        return akUserEntity.restaurants.map { Restaurant.fromEntity(it) }
    }

    @Transactional
    override fun createRestaurant(user: User, title: String, imgUrl: String?, description: String?): Restaurant {
        val akUserEntity = userRepository.findById(user.id).toKotlinOptionalOrFail()
        val akRestaurantEntity = AkRestaurantEntity()
        akRestaurantEntity.imgUrl = imgUrl
        akRestaurantEntity.description = description
        akRestaurantEntity.title = title
        akRestaurantEntity.users = listOf(akUserEntity)

        val save = restaurantRepository.save(akRestaurantEntity)
        return Restaurant.fromEntity(save)
    }

    override fun getRestaurantById(id: Int): Restaurant? {
        val found = restaurantRepository.findById(id)
        return if (found.isPresent) {
            found.map { t -> Restaurant.fromEntity(t) }.get()
        } else {
            null
        }
    }


    @Throws(IllegalStateException::class)
    @Transactional
    override fun userBelongsToRestaurant(user: User, restaurantId: Int): Boolean {
        val found = restaurantRepository.findById(restaurantId)
        val akRestaurantEntity = if (found.isPresent) found.get() else throw IllegalArgumentException("restaurant not found")
        return akRestaurantEntity.users.map { it.id }.contains(user.id)
    }

    @Throws(IllegalStateException::class)
    override fun validateUserBelongsToRestaurant(user: User, restaurantId: Int) {
        if (!userBelongsToRestaurant(user, restaurantId)) {
            throw IllegalStateException("user with id {${user.id}} doesn't belong to restaurant with id {$restaurantId}")
        }
    }
}