package com.ak.be.engine.controller.order.impl

import com.ak.be.engine.EngineApplicationTests
import com.ak.be.engine.controller.order.CREATE_ORDER
import com.ak.be.engine.controller.restaurant.dto.CreateOrderRequest
import com.ak.be.engine.controller.restaurant.dto.OrderDto
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus


class OrderControllerDefaultTest : EngineApplicationTests() {

    lateinit var restTemplateWithBasic: TestRestTemplate

    @Before
    fun setUp() {
        restTemplateWithBasic = testRestTemplate.withBasicAuth("ian", "ian")
    }

    @Test
    fun createOrder() {
        val result = restTemplateWithBasic.postForEntity(CREATE_ORDER, CreateOrderRequest(1, null, 1), OrderDto::class.java)
        Assert.assertNotNull(result)
        Assert.assertEquals(result.statusCode, HttpStatus.OK)
        Assert.assertNotNull(result)
    }
}