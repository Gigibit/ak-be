package com.ak.be.engine.service.order

import com.ak.be.engine.service.order.model.Order

interface OrderService {
    fun getOrders(restaurantId: Int, limit: Int, offset: Int): List<Order>
}
