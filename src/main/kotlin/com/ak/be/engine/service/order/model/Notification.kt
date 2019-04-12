package com.ak.be.engine.service.order.model

import java.time.LocalDateTime

data class Notification(val id: Int, val title: String, val createdAt: LocalDateTime, val description: String?)