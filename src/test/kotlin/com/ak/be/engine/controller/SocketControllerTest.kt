package com.ak.be.engine.controller

import com.ak.be.engine.EngineApplicationTests
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.stomp.StompFrameHandler
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
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

    @Before
    fun setup() {
        url = "ws://localhost:$port/gs-guide-websocket"
    }

    @Autowired
    private lateinit var messagingTemplate: SimpMessagingTemplate

    @Test
    fun create() {
        val stompClient = WebSocketStompClient(SockJsClient(Collections.singletonList<Transport>(WebSocketTransport(StandardWebSocketClient()))))
        stompClient.messageConverter = MappingJackson2MessageConverter()
        val uuid = UUID.randomUUID().toString()
        val stompSession = stompClient.connect(url, object : StompSessionHandlerAdapter() {

        }).get(1, TimeUnit.SECONDS)

//        stompSession.subscribe("$url/topic/$uuid", DefaultStompFrameHandler())
        stompSession.subscribe("$url/topic/greetings", DefaultStompFrameHandler())
//        val send = stompSession.send("$url/app/create/$uuid", "dddd")
        val send = stompSession.send("$url/app/hello", "dddd")
        send.receiptId


        completableFuture.get(10, TimeUnit.SECONDS)
    }


    private inner class DefaultStompFrameHandler : StompFrameHandler {
        override fun getPayloadType(stompHeaders: StompHeaders): Type {
            return String::class.java
        }

        override fun handleFrame(stompHeaders: StompHeaders, o: Any?) {
            completableFuture.complete(o as String?)
        }
    }
}