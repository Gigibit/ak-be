package com.ak.be.engine.service.menu

import com.ak.be.engine.db.repository.MenuRepository
import com.ak.be.engine.db.repository.RestaurantRepository
import com.ak.be.engine.service.model.Menu
import com.ak.be.engine.service.model.User
import com.ak.be.engine.service.model.fromEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MenuServiceDefault(val menuRepository: MenuRepository,
                         val restaurantRepository: RestaurantRepository) : MenuService {

    @Throws(IllegalStateException::class)
    @Transactional
    override fun findUsersByMenuId(menuId: Int): List<User> {
        val menu = menuRepository.findById(menuId)
        val users = menu.map {
            val restaurant = it.restaurant
                    ?: throw IllegalStateException("restaurant not found by menu id $menuId")
            restaurant.users.map { akUserEntity -> User.fromEntity(akUserEntity) }
        }
        return if (users.isPresent) {
            users.get()
        } else {
            ArrayList()
        }
    }

    @Throws(IllegalArgumentException::class)
    @Transactional
    override fun findMenuForRestaurantId(restaurantId: Int): List<Menu> {
        val findById = restaurantRepository.findById(restaurantId)
        val restaurant = if (findById.isPresent) findById.get() else throw IllegalArgumentException("restaurant not found by restaurant id $restaurantId")
        return restaurant.menuItems.map { Menu.fromEntity(it) }
    }

}

