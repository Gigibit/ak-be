package com.ak.be.engine.service.order.model

import com.ak.be.engine.service.table.model.Table
import java.time.LocalDateTime

data class Order(val id: Int, val createdAt: LocalDateTime, val user: User?, val table: Table?, val notification: Notification?)