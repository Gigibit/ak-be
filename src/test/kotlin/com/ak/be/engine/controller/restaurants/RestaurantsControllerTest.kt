package com.ak.be.engine.controller.restaurants

import com.ak.be.engine.EngineApplicationTests
import com.ak.be.engine.controller.dish.dto.GetDishesByRestaurantIdResponse
import com.ak.be.engine.controller.restaurant.*
import com.ak.be.engine.controller.restaurant.dto.GetOrdersResponse
import com.ak.be.engine.controller.restaurant.dto.RestaurantDto
import com.ak.be.engine.controller.table.dto.CreateTableForRestaurantRequest
import com.ak.be.engine.controller.table.dto.GetTablesByRestaurantIdResponse
import com.ak.be.engine.controller.table.dto.TableDto
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus


class RestaurantsControllerTest : EngineApplicationTests() {

    lateinit var restTemplateWithBasic: TestRestTemplate

    @Before
    fun setUp() {
        restTemplateWithBasic = testRestTemplate.withBasicAuth("ian", "ian")
    }

    @Test
    fun getDishesById() {

//        val plainCredentials = "ian:ian"
//        val base64Credentials = Base64.getEncoder().encodeToString(plainCredentials.toByteArray())
//
//
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.APPLICATION_JSON
//        headers.set("Authorization", "Basic $base64Credentials")
//
//
////        testRestTemplate.restTemplate.interceptors.add(BasicAuthenticationInterceptor("ian","ian"))
//
////        val result = testRestTemplate.exchange("/restaurants/{restaurantId}/dishes/".replace("{restaurantId}", "1", true), HttpMethod.POST, HttpEntity(null, headers), GetDishesByRestaurantIdResponse::class.java)
//
        val result = restTemplateWithBasic.getForEntity(GET_DISHES_BY_ID.replace("{restaurantId}", "1", true), GetDishesByRestaurantIdResponse::class.java)
        Assert.assertEquals(result.statusCode, HttpStatus.OK)
        Assert.assertNotNull(result)
        Assert.assertNotNull(result.body)
        Assert.assertTrue(result.body!!.dishes.isNotEmpty())
    }

    @Test
    fun getRestaurantById() {
        val restaurantId = 1
        val result = testRestTemplate.getForEntity(GET_RESTAURANT_BY_ID.replace("{restaurantId}", restaurantId.toString()), RestaurantDto::class.java)
        Assert.assertNotNull(result)
        Assert.assertEquals(result.statusCode, HttpStatus.OK)
        Assert.assertNotNull(result.body)
        Assert.assertTrue(result.body!!.id == restaurantId)

    }

    @Test
    fun getTablesById() {
        privateGetTablesById(1)
    }

    @Test
    fun createTableById() {
        val restaurantId = 1
        val createTableForRestaurantRequest = CreateTableForRestaurantRequest("New table", 10)
        val result = restTemplateWithBasic.postForEntity(CREATE_TABLES_BY_ID.replace("{restaurantId}", restaurantId.toString(), true), createTableForRestaurantRequest, TableDto::class.java)
        Assert.assertNotNull(result)

        val tables = privateGetTablesById(restaurantId)
        Assert.assertTrue(tables.contains(result.body))
    }

    @Test
    fun getOrders() {
        val restaurantId = 1
        val result = restTemplateWithBasic.getForEntity(GET_ORDERS_BY_ID.replace("{restaurantId}", restaurantId.toString()), GetOrdersResponse::class.java)
        Assert.assertNotNull(result)
        Assert.assertEquals(result.statusCode, HttpStatus.OK)
        Assert.assertNotNull(result.body)
        Assert.assertTrue(result.body!!.orders.isNotEmpty())
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
