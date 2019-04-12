package com.ak.be.engine.controller.restaurant

import com.ak.be.engine.controller.dish.dto.GetDishesByRestaurantIdResponse
import com.ak.be.engine.controller.restaurant.dto.RestaurantDto
import com.ak.be.engine.controller.table.dto.CreateTableForRestaurantRequest
import com.ak.be.engine.controller.table.dto.GetTablesByRestaurantIdResponse
import com.ak.be.engine.controller.table.dto.TableDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody


const val GET_DISHES_BY_ID = "/restaurants/{restaurantId}/dishes"
const val GET_TABLES_BY_ID = "/restaurants/{restaurantId}/tables"
const val CREATE_TABLES_BY_ID = "/restaurants/{restaurantId}/tables"
const val GET_RESTAURANT_BY_ID = "/restaurants/{restaurantId}"


interface RestaurantController {

    @GetMapping(GET_RESTAURANT_BY_ID)
    fun getRestaurantById(@PathVariable restaurantId: Int): RestaurantDto

    @GetMapping(GET_DISHES_BY_ID)
    fun getDishesById(@PathVariable restaurantId: Int): GetDishesByRestaurantIdResponse

    @GetMapping(GET_TABLES_BY_ID)
    fun getTablesById(@PathVariable restaurantId: Int): GetTablesByRestaurantIdResponse

    @PostMapping(CREATE_TABLES_BY_ID)
    fun createTableById(@PathVariable restaurantId: Int, @RequestBody request: CreateTableForRestaurantRequest): TableDto

}