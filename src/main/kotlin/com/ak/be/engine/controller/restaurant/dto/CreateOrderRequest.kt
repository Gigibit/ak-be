package com.ak.be.engine.controller.restaurant.dto

import javax.validation.constraints.NotEmpty

data class CreateOrderRequest(@NotEmpty val menuIds: List<Int>, val userId: Int?, val tableId: Int?)