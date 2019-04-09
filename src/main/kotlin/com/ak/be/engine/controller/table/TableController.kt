package com.ak.be.engine.controller.table

import com.ak.be.engine.controller.table.dto.GetTablesByRestaurantIdResponse
import com.ak.be.engine.controller.table.dto.RestaurantDto
import com.ak.be.engine.service.table.TableService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TableController {

    @Autowired
    lateinit var tableService: TableService

    @GetMapping("restaurants/{restaurantId}/tables")
    fun getDishesByRestaurantId(@PathVariable restaurantId: Int): GetTablesByRestaurantIdResponse {
        val list = tableService.getTablesByRestaurantsId(restaurantId).map { RestaurantDto(it.id, it.title, it.numberOfPlaces) }
        return GetTablesByRestaurantIdResponse(list)
    }
}