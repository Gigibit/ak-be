package com.ak.be.engine.service.notification

import com.ak.be.engine.service.model.Notification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.util.HtmlUtils

@Service
class NotificationServiceDefault(@Autowired val simpMessagingTemplate: SimpMessageSendingOperations) : NotificationService {
    @Async
    override fun sendNotification(userName: String, notification: Notification) {
        simpMessagingTemplate.convertAndSendToUser(userName, "/topic/orders", "{\"content\":\"Hello, " + HtmlUtils.htmlEscape(userName) + "\"}")
    }
}