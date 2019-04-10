package com.ak.be.engine.controller.table

import com.ak.be.engine.controller.table.dto.CreateTableForRestaurantRequest
import com.ak.be.engine.controller.table.dto.GetTablesByRestaurantIdResponse
import com.ak.be.engine.controller.table.dto.TableDto
import com.ak.be.engine.service.table.TableService
import com.ak.be.engine.service.table.model.Table
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class TableController {

    @Autowired
    lateinit var tableService: TableService

    @GetMapping("restaurants/{restaurantId}/tables")
    fun getDishesByRestaurantId(@PathVariable restaurantId: Int): GetTablesByRestaurantIdResponse {
        val list = tableService.getTablesByRestaurantsId(restaurantId).map(fromModelToDto)
        return GetTablesByRestaurantIdResponse(list)
    }

    @PostMapping("restaurants/{restaurantId}/tables")
    fun createTableForRestaurant(@PathVariable restaurantId: Int, @RequestBody request: CreateTableForRestaurantRequest): TableDto {
        val table = Table(0, request.title, request.numberOfPlaces)
        val createTablesForRestaurantsId = tableService.createTablesForRestaurantsId(restaurantId, table)
        return fromModelToDto(createTablesForRestaurantsId)
    }

    val fromModelToDto = { model: Table -> TableDto(model.id, model.title, model.numberOfPlaces) }

}