package com.ak.be.engine.controller

import com.ak.be.engine.EngineApplicationTests
import com.ak.be.engine.controller.dto.GetDishesByRestaurantIdResponse
import org.junit.Assert
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus


class DishControllerTest : EngineApplicationTests() {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun getDishesByRestaurantId() {
        val result = testRestTemplate.getForEntity("/restaurant/1/dishes", GetDishesByRestaurantIdResponse::class.java)
        Assert.assertNotNull(result)
        Assert.assertNotNull(result.body)
        Assert.assertTrue(result.body!!.dishes.isNotEmpty())
        Assert.assertEquals(result.statusCode, HttpStatus.OK)
    }

    @Test
    fun getDishById() {
        val result = testRestTemplate.getForEntity("/dishes/1", String::class.java)
        Assert.assertNotNull(result)
        Assert.assertEquals(result.statusCode, HttpStatus.OK)
    }

}
