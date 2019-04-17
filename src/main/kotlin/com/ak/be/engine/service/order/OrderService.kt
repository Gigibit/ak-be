package com.ak.be.engine.service.order

import com.ak.be.engine.service.model.Order

interface OrderService {
    fun getOrders(restaurantId: Int, limit: Int, offset: Int): List<Order>
    fun createOrder(menuId: Int, tableId: Int?, userId: Int?): Order
}
