package com.ak.be.engine.controller.restaurant

import com.ak.be.engine.controller.restaurant.dto.CreateTableForRestaurantResponse
import com.ak.be.engine.controller.restaurant.dto.GetMenuByRestaurantResponse
import com.ak.be.engine.controller.restaurant.dto.GetOrdersResponse
import com.ak.be.engine.controller.restaurant.dto.RestaurantDto
import com.ak.be.engine.controller.table.dto.CreateTableForRestaurantRequest
import com.ak.be.engine.controller.table.dto.GetTablesForRestaurantResponse
import org.springframework.web.bind.annotation.*


const val GET_MENU_BY_ID = "/public/restaurants/{restaurantId}/menu"
const val GET_TABLES_BY_ID = "/restaurants/{restaurantId}/tables"
const val CREATE_TABLES_BY_ID = "/restaurants/{restaurantId}/tables"
const val GET_RESTAURANT_BY_ID = "/restaurants/{restaurantId}"
const val GET_ORDERS_BY_ID = "/restaurants/{restaurantId}/orders"

interface RestaurantController {

    @GetMapping(GET_RESTAURANT_BY_ID)
    fun getRestaurantById(@PathVariable restaurantId: Int): RestaurantDto

    @GetMapping(GET_TABLES_BY_ID)
    fun getTablesForRestaurant(@PathVariable restaurantId: Int): GetTablesForRestaurantResponse

    @PostMapping(CREATE_TABLES_BY_ID)
    fun createTableForRestaurant(@PathVariable restaurantId: Int, @RequestBody request: CreateTableForRestaurantRequest): CreateTableForRestaurantResponse

    @GetMapping(GET_ORDERS_BY_ID)
    fun getOrdersForRestaurant(@PathVariable restaurantId: Int, @RequestParam(value = "limit") limit: Int?, @RequestParam(value = "offset") offset: Int?): GetOrdersResponse

    @GetMapping(GET_MENU_BY_ID)
    fun getMenuForRestaurant(@PathVariable restaurantId: Int): GetMenuByRestaurantResponse
}