package com.ak.be.engine.service.order

import com.ak.be.engine.db.entity.*
import com.ak.be.engine.db.repository.*
import com.ak.be.engine.service.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderServiceDefault(@Autowired val orderRepository: OrderRepository,
                          @Autowired val menuRepository: MenuRepository,
                          @Autowired val tableRepository: TableRepository,
                          @Autowired val userRepository: UserRepository,
                          @Autowired val menuOrderRepository: MenuOrderRepository) : OrderService {

    @Transactional
    override fun createOrder(menuIds: List<Int>, tableId: Int?, userId: Int?): Order {


        val foundMenus = menuRepository.findAllById(menuIds).toList()
        if (foundMenus.size != menuIds.size) {
            throw IllegalStateException("menu entities found don't match menu ids")
        }

        if (foundMenus.map { akMenuEntity -> akMenuEntity.restaurant?.id }.distinct().size != 1) {
            throw IllegalStateException("menu entities belong to different restaurants")
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


        val akNotificationEntity = AkNotificationEntity()
        akNotificationEntity.title = "New Order"
        akNotificationEntity.description = "Order has been created"

        val akOrderEntity = AkOrderEntity()
        akOrderEntity.akUserByUserId = user
        akOrderEntity.akTableByTableId = table
        akOrderEntity.akNotificationByNotificationId = akNotificationEntity
        val savedOrder = orderRepository.save(akOrderEntity)

        val savedMenus = ArrayList<AkMenuOrderEntity>()
        for (foundMenu in foundMenus) {
            val akMenuOrderEntity = AkMenuOrderEntity()
            akMenuOrderEntity.akMenuByMenuId = foundMenu
            akMenuOrderEntity.akOrderByOrderId = savedOrder
            val save = menuOrderRepository.save(akMenuOrderEntity)
            savedMenus.add(save)
        }

        return fromEntityOrderToModel(savedOrder, savedMenus)
    }

    override fun getOrders(restaurantId: Int, limit: Int, offset: Int): List<Order> {
        return orderRepository.findOrdersByRestaurantId(restaurantId, limit, offset)
                .map { akOrderEntity: AkOrderEntity ->
                    val menuOrders = menuOrderRepository.findAllByOrderId(akOrderEntity.id)
                    fromEntityOrderToModel(akOrderEntity, menuOrders)
                }
    }

    val fromEntityOrderToModel = { it: AkOrderEntity, menuOrders: List<AkMenuOrderEntity> ->
        val table: Table? = it.akTableByTableId?.let { t: AkTableEntity ->
            Table(t.id, t.title, t.numberOfPlaces)
        }

        val user = it.akUserByUserId?.let { userEntity: AkUserEntity ->
            val authorities = userEntity.authorities?.map { Authority(it.id, it.name) } ?: ArrayList()
            User(userEntity.id,
                    userEntity.email,
                    userEntity.passwordHash,
                    userEntity.firstName,
                    userEntity.lastName,
                    authorities,
                    userEntity.accountExpired,
                    userEntity.accountLocked,
                    userEntity.credentialsExpired,
                    userEntity.enabled
            )
        }

        val notification = it.akNotificationByNotificationId?.let { n: AkNotificationEntity ->
            val createdAt = it.createdAt?.toLocalDateTime()
                    ?: throw IllegalStateException("createdAt is null")
            Notification(n.id, n.title, createdAt, n.description)
        } ?: throw IllegalStateException("Notification is null")


        val menus = ArrayList<Menu>()
        for (menuOrder in menuOrders) {
            val menu = menuOrder.akMenuByMenuId?.let { n: AkMenuEntity ->
                val dish = n.akDishByDishId?.let { d: AkDishEntity ->
                    Dish(d.id, d.title)
                } ?: throw IllegalStateException("Dish is null")
                Menu(n.id, n.state, n.type, dish)
            } ?: throw IllegalStateException("Menu is null")
            menus.add(menu)
        }

        val createdAt = it.createdAt?.toLocalDateTime()
                ?: throw IllegalStateException("createdAt is null")

        Order(it.id, createdAt, menus, user, table, notification)
    }

}