package com.ak.be.engine.controller.restaurants

import com.ak.be.engine.EngineApplicationTests
import com.ak.be.engine.controller.dish.dto.GetDishesByRestaurantIdResponse
import com.ak.be.engine.controller.restaurant.CREATE_TABLES_BY_ID
import com.ak.be.engine.controller.restaurant.GET_DISHES_BY_ID
import com.ak.be.engine.controller.restaurant.GET_TABLES_BY_ID
import com.ak.be.engine.controller.table.dto.CreateTableForRestaurantRequest
import com.ak.be.engine.controller.table.dto.GetTablesByRestaurantIdResponse
import com.ak.be.engine.controller.table.dto.TableDto
import org.junit.Assert
import org.junit.Test
import org.springframework.http.HttpStatus


class RestaurantsControllerTest : EngineApplicationTests() {

    @Test
    fun getDishesById() {
        val result = testRestTemplate.getForEntity(GET_DISHES_BY_ID.replace("{restaurantId}", "1", true), GetDishesByRestaurantIdResponse::class.java)
        Assert.assertNotNull(result)
        Assert.assertNotNull(result.body)
        Assert.assertTrue(result.body!!.dishes.isNotEmpty())
        Assert.assertEquals(result.statusCode, HttpStatus.OK)
    }

    @Test
    fun getTablesById() {
        privateGetTablesById(1)
    }

    @Test
    fun createTableById() {
        val restaurantId = 1
        val createTableForRestaurantRequest = CreateTableForRestaurantRequest("New table", 10)
        val result = testRestTemplate.postForEntity(CREATE_TABLES_BY_ID.replace("{restaurantId}", restaurantId.toString(), true), createTableForRestaurantRequest, TableDto::class.java)
        Assert.assertNotNull(result)

        val tables = privateGetTablesById(restaurantId)
        Assert.assertTrue(tables.contains(result.body))
    }

    private fun privateGetTablesById(restaurantId: Int): List<TableDto> {
        val tables = testRestTemplate.getForEntity(GET_TABLES_BY_ID.replace("{restaurantId}", restaurantId.toString(), true), GetTablesByRestaurantIdResponse::class.java)
        Assert.assertNotNull(tables)
        Assert.assertEquals(tables.statusCode, HttpStatus.OK)
        Assert.assertNotNull(tables.body)
        Assert.assertTrue(tables.body!!.tables.isNotEmpty())
        return tables.body!!.tables
    }


}
