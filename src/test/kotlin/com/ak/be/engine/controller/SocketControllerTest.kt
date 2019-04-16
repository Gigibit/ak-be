package com.ak.be.engine.controller

import com.ak.be.engine.EngineApplicationTests
import com.ak.be.engine.controller.restaurant.GET_RESTAURANT_BY_ID
import com.ak.be.engine.controller.restaurant.dto.RestaurantDto
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.web.socket.WebSocketHttpHeaders
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import org.springframework.web.socket.sockjs.client.SockJsClient
import org.springframework.web.socket.sockjs.client.Transport
import org.springframework.web.socket.sockjs.client.WebSocketTransport
import java.lang.reflect.Type
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit


internal class SocketControllerTest : EngineApplicationTests() {

    private var completableFuture: CompletableFuture<String> = CompletableFuture()
    @Value("\${local.server.port}")
    private val port: Int = 0
    lateinit var url: String

    lateinit var restTemplateWithBasic: TestRestTemplate
    lateinit var stompClientWithBasic: WebSocketStompClient

    @Before
    fun setup() {
        url = "ws://localhost:$port/ak-websocket"
        restTemplateWithBasic = testRestTemplate.withBasicAuth("ian", "ian")
        stompClientWithBasic = WebSocketStompClient(SockJsClient(Collections.singletonList<Transport>(WebSocketTransport(StandardWebSocketClient()))))
    }

    @Test
    fun create() {
        stompClientWithBasic.messageConverter = MappingJackson2MessageConverter()
        val uuid = UUID.randomUUID().toString()

        val plainCredentials = "ian:ian"
        val base64Credentials = Base64.getEncoder().encodeToString(plainCredentials.toByteArray())

        val headers = WebSocketHttpHeaders()
        headers.add("Authorization", "Basic $base64Credentials")

        val stompSession: StompSession = stompClientWithBasic.connect(url, headers, object : StompSessionHandlerAdapter() {
            override fun handleException(session: StompSession, command: StompCommand?, headers: StompHeaders, payload: ByteArray, exception: Throwable) {
                val logger = LoggerFactory.getLogger(this::class.java)
                logger.info("handleException")
                super.handleException(session, command, headers, payload, exception)
            }

            override fun handleTransportError(session: StompSession, exception: Throwable) {
                val logger = LoggerFactory.getLogger(this::class.java)
                logger.info("handleTransportError")
                super.handleTransportError(session, exception)
            }

            override fun afterConnected(session: StompSession, connectedHeaders: StompHeaders) {
                val logger = LoggerFactory.getLogger(this::class.java)
                logger.info("afterConnected")
                super.afterConnected(session, connectedHeaders)
            }

        }).get(1, TimeUnit.SECONDS)
//        stompSession.setAutoReceipt(true)
//
//        stompSession.subscribe("$url/topic/$uuid", DefaultStompFrameHandler())
        stompSession.subscribe("$url/user/topic/orders", object : StompSessionHandlerAdapter() {
//        override fun getPayloadType(stompHeaders: StompHeaders): Type {
//            return String::class.java
//        }

            override fun handleFrame(stompHeaders: StompHeaders, o: Any?) {
                val logger = LoggerFactory.getLogger(this::class.java)
                logger.info("handleFrame")
                completableFuture.complete(o as String?)
            }


            /**
             * This implementation is empty.
             */
            override fun handleException(session: StompSession, command: StompCommand?, headers: StompHeaders, payload: ByteArray, exception: Throwable) {
                val logger = LoggerFactory.getLogger(this::class.java)
                logger.info("handleException")
                super.handleException(session, command, headers, payload, exception)
            }

            /**
             * This implementation is empty.
             */
            override fun handleTransportError(session: StompSession, exception: Throwable) {
                val logger = LoggerFactory.getLogger(this::class.java)
                logger.info("handleTransportError")
                super.handleTransportError(session, exception)
            }

            /**
             * This implementation is empty.
             */
            override fun afterConnected(session: StompSession, connectedHeaders: StompHeaders) {
                val logger = LoggerFactory.getLogger(this::class.java)
                logger.info("afterConnected")
                super.afterConnected(session, connectedHeaders)
            }

            /**
             * This implementation returns String as the expected payload type
             * for STOMP ERROR frames.
             */
            override fun getPayloadType(headers: StompHeaders): Type {
                val logger = LoggerFactory.getLogger(this::class.java)
                logger.info("getPayloadType")
                return super.getPayloadType(headers)
            }
        })
        Thread.sleep(10000)
//        stompSession.send("jms.queue.test", "hello!")
        val restaurantId = 1
        val result = restTemplateWithBasic.getForEntity(GET_RESTAURANT_BY_ID.replace("{restaurantId}", restaurantId.toString()), RestaurantDto::class.java)
        Assert.assertEquals(result.statusCode, HttpStatus.OK)
//        val send = stompSession.send("$url/app/create/$uuid", "dddd")
//        val send = stompSession.send("$url/app/hello", "dddd")
//        send.receiptId


        completableFuture.get(20, TimeUnit.SECONDS)
    }

}