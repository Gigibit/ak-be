package com.ak.be.engine.controller.restaurant.dto

import com.ak.be.engine.controller.table.dto.TableDto
import java.time.LocalDateTime

data class OrderDto(val id: Int, val createdAt: LocalDateTime, val menu: MenuDto, val user: UserDto?, val table: TableDto?, val notification: NotificationDto?)
