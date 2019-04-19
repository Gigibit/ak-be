package com.ak.be.engine.service.model

import com.ak.be.engine.controller.restaurant.dto.OrderDto
import java.time.LocalDateTime

data class Order(val id: Int, val createdAt: LocalDateTime, val menu: List<Menu>, val user: User?, val table: Table?, val notification: Notification)

fun Order.toDto(): OrderDto {
    val userDto = this.user?.let { u: User -> u.toDto() }
    val tableDto = this.table?.let { u: Table -> u.toDto() }
    val notificationDto = this.notification.let { n: Notification -> n.toDto() }
    val menuDtos = this.menu.map { menu -> menu.toDto() }
    return OrderDto(this.id, this.createdAt, menuDtos, userDto, tableDto, notificationDto)
}