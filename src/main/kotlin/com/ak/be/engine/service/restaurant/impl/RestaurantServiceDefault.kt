package com.ak.be.engine.service.restaurant.impl

import com.ak.be.engine.db.entity.AkRestaurantEntity
import com.ak.be.engine.db.repository.RestaurantRepository
import com.ak.be.engine.service.model.Restaurant
import com.ak.be.engine.service.model.User
import com.ak.be.engine.service.restaurant.RestaurantService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RestaurantServiceDefault(@Autowired val restaurantRepository: RestaurantRepository) : RestaurantService {

    override fun getRestaurantById(id: Int): Restaurant? {
        val found = restaurantRepository.findById(id)
        return if (found.isPresent) {
            found.map(mapper).get()
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

    private val mapper: (AkRestaurantEntity) -> Restaurant = { t -> Restaurant(t.id, t.title, t.imgUrl, t.description) }

}