package com.ak.be.engine.controller.restaurant.dto

import com.ak.be.engine.controller.table.dto.TableDto
import java.time.LocalDateTime

data class OrderDto(val id: Int, val createdAt: LocalDateTime, val userDto: UserDto?, val tableDto: TableDto?, val notificationDto: NotificationDto?)
