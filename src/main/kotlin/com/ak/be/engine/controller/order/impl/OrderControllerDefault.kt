package com.ak.be.engine.controller.order.impl

import com.ak.be.engine.controller.order.OrderController
import com.ak.be.engine.controller.restaurant.dto.CreateOrderRequest
import com.ak.be.engine.controller.restaurant.dto.OrderDto
import com.ak.be.engine.service.model.Order
import com.ak.be.engine.service.model.toDto
import com.ak.be.engine.service.notification.NotificationService
import com.ak.be.engine.service.order.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class OrderControllerDefault(@Autowired val orderService: OrderService, @Autowired val notificationService: NotificationService) : OrderController {

    override fun createOrder(@Valid @RequestBody request: CreateOrderRequest): OrderDto {

        val menuIds = request.menuIds

        val tableId = request.tableId
        val userId = request.userId
        if (tableId == null && userId == null) {
            throw IllegalArgumentException("at least tableId or userId must not be null")
        }
        val order: Order = orderService.createOrder(menuIds, tableId, userId)
        //send notification
        //find user of restaurant in db by restaurant id
        val userName = "ian"
        notificationService.sendNotification(userName, order.notification)
        return order.toDto()
    }
}