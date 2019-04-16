package com.ak.be.engine.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.messaging.simp.annotation.SendToUser
import org.springframework.stereotype.Controller
import org.springframework.web.util.HtmlUtils
import java.security.Principal


@Controller
class SocketController(@Autowired val simpMessagingTemplate: SimpMessageSendingOperations) {
    val logger: Logger = LoggerFactory.getLogger(SocketController::class.java)
    @MessageMapping("/app/create/{uuid}")
    @SendTo("/app/topic/{uuid}")
//    @Throws(IllegalArgumentException::class)
    fun create(@DestinationVariable uuid: String): String {
        logger.info("uiid$uuid")
        return "Hello World!"

    }


    @MessageMapping("/hello")
    @SendToUser("/topic/orders")
    @Throws(Exception::class)
    fun greeting(principal: Principal, message: String): String {
        Thread.sleep(1000) // simulated delay
        simpMessagingTemplate.convertAndSendToUser(principal.name, "/topic/orders", "{\"content\":\"Hello, " + HtmlUtils.htmlEscape(principal.name) + "\"}")
        return "{\"content\":\"Hello, " + HtmlUtils.htmlEscape(message) + "\"}"
    }
}