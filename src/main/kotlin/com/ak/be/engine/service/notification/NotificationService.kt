package com.ak.be.engine.service.notification

import com.ak.be.engine.service.model.Notification

interface NotificationService {
    fun sendNotification(userName: String, notification: Notification)
}
