package com.ak.be.engine.controller.restaurant.dto

data class CreateOrderRequest(val menuId: Int, val userId: Int?, val tableId: Int?)