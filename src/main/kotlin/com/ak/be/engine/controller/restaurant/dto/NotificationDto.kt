package com.ak.be.engine.controller.restaurant.dto

import java.time.LocalDateTime

data class NotificationDto(val id: Int, val title: String, val createdAt: LocalDateTime, val description: String?)
