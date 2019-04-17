package com.ak.be.engine.service.model

import com.ak.be.engine.controller.restaurant.dto.NotificationDto
import java.time.LocalDateTime

data class Notification(val id: Int,
                        val title: String,
                        val createdAt: LocalDateTime,
                        val description: String?)

fun Notification.toDto(): NotificationDto {
    return NotificationDto(this.id, this.title, this.createdAt, this.description)
}