package com.ak.be.engine.controller.order

import com.ak.be.engine.controller.restaurant.dto.CreateOrderRequest
import com.ak.be.engine.controller.restaurant.dto.OrderDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

const val CREATE_ORDER = "/orders"

interface OrderController {
    @PostMapping(CREATE_ORDER)
    fun createOrder(@Valid @RequestBody request: CreateOrderRequest): OrderDto
}
