package com.ak.be.engine.service.menu

import com.ak.be.engine.service.model.Menu
import com.ak.be.engine.service.model.User

interface MenuService {
    fun findUsersByMenuId(menuId: Int): List<User>
    fun findMenuForRestaurantId(restaurantId: Int): List<Menu>
}