package com.ak.be.engine.controller.order.impl

import com.ak.be.engine.controller.order.OrderController
import com.ak.be.engine.controller.restaurant.dto.CreateOrderRequest
import com.ak.be.engine.controller.restaurant.dto.OrderDto
import com.ak.be.engine.service.model.Order
import com.ak.be.engine.service.model.toDto
import com.ak.be.engine.service.order.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderControllerDefault(@Autowired val orderService: OrderService) : OrderController {

    override fun createOrder(@RequestBody request: CreateOrderRequest): OrderDto {
        val menuId = request.menuId
        val tableId = request.tableId
        val userId = request.userId
        if (tableId == null && userId == null) {
            throw IllegalArgumentException("at least tableId or userId must not be null")
        }
        val order: Order = orderService.createOrder(menuId, tableId, userId)

        //send notification
        return order.toDto()
    }
}