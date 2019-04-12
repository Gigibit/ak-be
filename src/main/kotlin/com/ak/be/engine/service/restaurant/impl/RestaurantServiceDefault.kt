package com.ak.be.engine.service.restaurant.impl

import com.ak.be.engine.db.entity.AkRestaurantEntity
import com.ak.be.engine.db.repository.RestaurantRepository
import com.ak.be.engine.service.restaurant.RestaurantService
import com.ak.be.engine.service.restaurant.model.Restaurant
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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

    private val mapper: (AkRestaurantEntity) -> Restaurant = { t -> Restaurant(t.id, t.title, t.imgUrl, t.description) }

}