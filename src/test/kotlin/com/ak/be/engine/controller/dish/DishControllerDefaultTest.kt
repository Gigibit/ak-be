package com.ak.be.engine.controller.dish

import com.ak.be.engine.EngineApplicationTests
import org.junit.Assert
import org.junit.Test
import org.springframework.http.HttpStatus


class DishControllerDefaultTest : EngineApplicationTests() {

    @Test
    fun getDishById() {
        val result = testRestTemplate.getForEntity(GET_DISH_BY_ID.replace("{id}", "1"), String::class.java)
        Assert.assertNotNull(result)
        Assert.assertEquals(result.statusCode, HttpStatus.OK)
    }

}
