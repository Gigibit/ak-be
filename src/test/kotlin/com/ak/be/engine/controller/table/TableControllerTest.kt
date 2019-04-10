package com.ak.be.engine.controller.table

import com.ak.be.engine.EngineApplicationTests
import com.ak.be.engine.controller.table.dto.CreateTableForRestaurantRequest
import com.ak.be.engine.controller.table.dto.GetTablesByRestaurantIdResponse
import com.ak.be.engine.controller.table.dto.TableDto
import org.junit.Assert
import org.junit.Test
import org.springframework.http.HttpStatus

class TableControllerTest : EngineApplicationTests() {


    @Test
    fun getTablesByRestaurantId() {
        val result = testRestTemplate.getForEntity("/restaurants/1/tables", GetTablesByRestaurantIdResponse::class.java)
        Assert.assertNotNull(result)
        Assert.assertNotNull(result.body)
        Assert.assertTrue(result.body!!.tables.isNotEmpty())
        Assert.assertEquals(result.statusCode, HttpStatus.OK)
    }

    @Test
    fun createTableForRestaurant() {

        val createTableForRestaurantRequest = CreateTableForRestaurantRequest("New table", 10)
        val result = testRestTemplate.postForEntity("/restaurants/1/tables", createTableForRestaurantRequest, TableDto::class.java)
        Assert.assertNotNull(result)

    }
}