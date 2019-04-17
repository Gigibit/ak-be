package com.ak.be.engine.service.order

import com.ak.be.engine.db.entity.*
import com.ak.be.engine.db.repository.MenuRepository
import com.ak.be.engine.db.repository.OrderRepository
import com.ak.be.engine.db.repository.TableRepository
import com.ak.be.engine.db.repository.UserRepository
import com.ak.be.engine.service.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderServiceDefault(@Autowired val orderRepository: OrderRepository, @Autowired val menuRepository: MenuRepository, @Autowired val tableRepository: TableRepository, @Autowired val userRepository: UserRepository) : OrderService {

    @Transactional
    override fun createOrder(menuId: Int, tableId: Int?, userId: Int?): Order {

        val menuFound = menuRepository.findById(menuId)
        if (!menuFound.isPresent) {
            throw IllegalStateException("menu not found")
        }


        val table = tableId?.let {
            val found = tableRepository.findById(it)
            if (found.isPresent) {
                found.get()
            } else {
                null
            }
        }

        val user = userId?.let {
            val found = userRepository.findById(it)
            if (found.isPresent) {
                found.get()
            } else {
                null
            }
        }

        if (table == null && user == null) {
            throw IllegalStateException("table and user not found")
        }


        val menu = menuFound.get()

        val akNotificationEntity = AkNotificationEntity()
        akNotificationEntity.title = "New Order"
        akNotificationEntity.description = "Order has been created"

        val akOrderEntity = AkOrderEntity()
        akOrderEntity.akUserByUserId = user
        akOrderEntity.akTableByTableId = table
        akOrderEntity.akMenuByMenuId = menu
        akOrderEntity.akNotificationByNotificationId = akNotificationEntity
        val save = orderRepository.save(akOrderEntity)
        return fromEntityOrderToModel(save)
    }

    override fun getOrders(restaurantId: Int, limit: Int, offset: Int): List<Order> {
        return orderRepository.findOrdersByRestaurantId(restaurantId, limit, offset).map(fromEntityOrderToModel)
    }

    val fromEntityOrderToModel = { it: AkOrderEntity ->
        val table: Table? = it.akTableByTableId?.let { t: AkTableEntity ->
            Table(t.id, t.title, t.numberOfPlaces)
        }

        val user = it.akUserByUserId?.let { u: AkUserEntity ->
            User(u.id, u.email, u.firstName, u.lastName)
        }

        val notification = it.akNotificationByNotificationId?.let { n: AkNotificationEntity ->
            val createdAt = it.createdAt?.toLocalDateTime()
                    ?: throw IllegalStateException("createdAt is null")
            Notification(n.id, n.title, createdAt, n.description)
        } ?: throw IllegalStateException("Notification is null")

        val menu = it.akMenuByMenuId?.let { n: AkMenuEntity ->
            val dish = n.akDishByDishId?.let { d: AkDishEntity ->
                Dish(d.id, d.title)
            } ?: throw IllegalStateException("Dish is null")
            Menu(n.id, n.state, n.type, dish)
        } ?: throw IllegalStateException("Menu is null")

        val createdAt = it.createdAt?.toLocalDateTime()
                ?: throw IllegalStateException("createdAt is null")

        Order(it.id, createdAt, menu, user, table, notification)
    }

}