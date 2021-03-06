package com.ak.be.engine.controller.order.impl

import com.ak.be.engine.controller.order.OrderController
import com.ak.be.engine.controller.restaurant.dto.CreateOrderRequest
import com.ak.be.engine.controller.restaurant.dto.OrderDto
import com.ak.be.engine.service.auth.AuthService
import com.ak.be.engine.service.menu.MenuService
import com.ak.be.engine.service.model.Order
import com.ak.be.engine.service.model.toDto
import com.ak.be.engine.service.notification.NotificationService
import com.ak.be.engine.service.order.OrderService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class OrderControllerDefault(val orderService: OrderService,
                             val notificationService: NotificationService,
                             val menuService: MenuService,
                             val authService: AuthService) : OrderController {
    @PreAuthorize("hasAuthority('USER')")
    override fun createOrder(@Valid @RequestBody request: CreateOrderRequest): OrderDto {

        val userOrFail = authService.getUserOrFail()

        val menuIds = request.menuIds
        val tableId = request.tableId
        val userId = request.userId

        if (tableId == null && userId == null) {
            throw IllegalArgumentException("at least tableId or userId must not be null")
        }
        val order: Order = orderService.createOrder(menuIds, tableId, userId)

        if (menuIds.isNotEmpty()) {
            val menuId = menuIds.first()
            val users = menuService.findUsersByMenuId(menuId)
            for (user in users) {
                //send notification
                notificationService.sendNotification(user.email, order.notification)
            }
        } else {
            throw IllegalStateException("menuIds are empty")
        }

        return order.toDto()
    }
}

