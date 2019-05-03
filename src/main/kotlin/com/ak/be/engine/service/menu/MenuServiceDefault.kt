package com.ak.be.engine.service.menu

import com.ak.be.engine.db.repository.MenuRepository
import com.ak.be.engine.service.model.User
import com.ak.be.engine.service.model.fromEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MenuServiceDefault(@Autowired val menuRepository: MenuRepository) : MenuService {
    @Transactional
    override fun findUsersByMenuId(menuId: Int): List<User> {
        val menu = menuRepository.findById(menuId)
        val users = menu.map {
            val restaurant = it.restaurant
                    ?: throw IllegalStateException("restaurant not found by menu id $menuId")
            restaurant.users?.map { akUserEntity -> User.fromEntity(akUserEntity) } ?: ArrayList()
        }
        return if (users.isPresent) {
            users.get()
        } else {
            ArrayList()
        }
    }

}

