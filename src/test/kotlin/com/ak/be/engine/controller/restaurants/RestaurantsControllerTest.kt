package com.ak.be.engine.controller.restaurants

import com.ak.be.engine.EngineApplicationTests
import com.ak.be.engine.controller.error.AkError
import com.ak.be.engine.controller.restaurant.*
import com.ak.be.engine.controller.restaurant.dto.CreateTableForRestaurantResponse
import com.ak.be.engine.controller.restaurant.dto.GetMenuByRestaurantResponse
import com.ak.be.engine.controller.restaurant.dto.GetOrdersResponse
import com.ak.be.engine.controller.restaurant.dto.RestaurantDto
import com.ak.be.engine.controller.table.dto.CreateTableForRestaurantRequest
import com.ak.be.engine.controller.table.dto.GetTablesForRestaurantResponse
import com.ak.be.engine.controller.table.dto.TableDto
import org.junit.Assert
import org.junit.Test
import org.springframework.http.HttpStatus


class RestaurantsControllerTest : EngineApplicationTests() {

    @Test
    fun getMenuForRestaurant() {
        authenticate()
        val result = testRestTemplate.getForEntity(GET_MENU_BY_ID.replace("{restaurantId}", "1"), GetMenuByRestaurantResponse::class.java)
        Assert.assertEquals(result.statusCode, HttpStatus.OK)
        Assert.assertNotNull(result)
        Assert.assertNotNull(result.body)
        Assert.assertTrue(result.body!!.menuItems.isNotEmpty())
    }

    @Test
    fun getRestaurantById() {
        authenticate()
        val restaurantId = 1
        val result = testRestTemplate.getForEntity(GET_RESTAURANT_BY_ID.replace("{restaurantId}", restaurantId.toString()), RestaurantDto::class.java)
        Assert.assertNotNull(result)
        Assert.assertEquals(result.statusCode, HttpStatus.OK)
        Assert.assertNotNull(result.body)
        Assert.assertTrue(result.body!!.id == restaurantId)
    }


    @Test
    fun getTablesForRestaurant() {
        authenticate()
        privateGetTablesById(1)
    }


    @Test
    fun getOrdersForRestaurant() {
        authenticate()
        val restaurantId = 1
        val result = testRestTemplate.getForEntity(GET_ORDERS_BY_ID.replace("{restaurantId}", restaurantId.toString()), GetOrdersResponse::class.java)
        Assert.assertNotNull(result)
        Assert.assertEquals(result.statusCode, HttpStatus.OK)
        Assert.assertNotNull(result.body)
        Assert.assertTrue(result.body!!.orders.isNotEmpty())
    }

    @Test
    fun createTableForRestaurant() {
        authenticate()
        val restaurantId = 1
        val createTableForRestaurantRequest = CreateTableForRestaurantRequest("New table", 10)
        val result = testRestTemplate.postForEntity(CREATE_TABLES_BY_ID.replace("{restaurantId}", restaurantId.toString()), createTableForRestaurantRequest, CreateTableForRestaurantResponse::class.java)
        Assert.assertNotNull(result)
        Assert.assertNotNull(result.body)

        val tables = privateGetTablesById(restaurantId)
        Assert.assertTrue(tables.contains(result.body!!.table))
    }

    @Test
    fun createTableForRestaurantNotExistingRestaurant() {
        authenticate()
        val restaurantId = 100
        val createTableForRestaurantRequest = CreateTableForRestaurantRequest("New table", 10)
        val result = testRestTemplate.postForEntity(CREATE_TABLES_BY_ID.replace("{restaurantId}", restaurantId.toString()), createTableForRestaurantRequest, AkError::class.java)
        Assert.assertNotNull(result)
        Assert.assertNotNull(result.body)
        Assert.assertTrue(result.body!!.message.contains("restaurant not found"))
    }


    private fun privateGetTablesById(restaurantId: Int): List<TableDto> {
        val tables = testRestTemplate.getForEntity(GET_TABLES_BY_ID.replace("{restaurantId}", restaurantId.toString(), true), GetTablesForRestaurantResponse::class.java)
        Assert.assertNotNull(tables)
        Assert.assertEquals(tables.statusCode, HttpStatus.OK)
        Assert.assertNotNull(tables.body)
        Assert.assertTrue(tables.body!!.tables.isNotEmpty())
        return tables.body!!.tables
    }

}