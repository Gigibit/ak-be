package com.ak.be.engine.service.order

import com.ak.be.engine.db.entity.AkNotificationEntity
import com.ak.be.engine.db.entity.AkTableEntity
import com.ak.be.engine.db.entity.AkUserEntity
import com.ak.be.engine.db.repository.OrderRepository
import com.ak.be.engine.service.order.model.Notification
import com.ak.be.engine.service.order.model.Order
import com.ak.be.engine.service.order.model.User
import com.ak.be.engine.service.table.model.Table
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderServiceDefault(@Autowired val orderRepository: OrderRepository) : OrderService {
    override fun getOrders(restaurantId: Int, limit: Int, offset: Int): List<Order> {
        return orderRepository.findOrdersByRestaurantId(restaurantId, limit, offset).map {

            val table: Table? = it.akTableByTableId?.let { t: AkTableEntity ->
                Table(t.id, t.title, t.numberOfPlaces)
            }


            val user = it.akUserByUserId?.let { u: AkUserEntity ->
                User(u.id, u.email, u.firstName, u.lastName)
            }

            val notification = it.akNotificationByNotificationId?.let { n: AkNotificationEntity ->
                Notification(n.id, n.title, n.createdAt.toLocalDateTime(), n.description)
            }
            Order(it.id, it.createdAt.toLocalDateTime(), user, table, notification)
        }
    }

}