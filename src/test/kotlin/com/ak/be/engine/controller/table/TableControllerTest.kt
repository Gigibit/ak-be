package com.ak.be.engine.controller.table

import com.ak.be.engine.EngineApplicationTests
import com.ak.be.engine.controller.table.dto.GetTablesByRestaurantIdResponse
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
}