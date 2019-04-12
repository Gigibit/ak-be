package com.ak.be.engine.controller.restaurant.impl

import com.ak.be.engine.controller.dish.dto.DishDto
import com.ak.be.engine.controller.dish.dto.GetDishesByRestaurantIdResponse
import com.ak.be.engine.controller.restaurant.RestaurantController
import com.ak.be.engine.controller.restaurant.dto.RestaurantDto
import com.ak.be.engine.controller.table.dto.CreateTableForRestaurantRequest
import com.ak.be.engine.controller.table.dto.GetTablesByRestaurantIdResponse
import com.ak.be.engine.controller.table.dto.TableDto
import com.ak.be.engine.service.dish.DishService
import com.ak.be.engine.service.restaurant.RestaurantService
import com.ak.be.engine.service.restaurant.model.Restaurant
import com.ak.be.engine.service.table.TableService
import com.ak.be.engine.service.table.model.Table
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class RestaurantControllerDefault(@Autowired val dishService: DishService,
                                  @Autowired val tableService: TableService,
                                  @Autowired val restaurantService: RestaurantService) : RestaurantController {

    override fun getRestaurantById(@PathVariable restaurantId: Int): RestaurantDto {
        val restaurantById = restaurantService.getRestaurantById(restaurantId)
        if (restaurantById == null) {
            throw IllegalArgumentException("not found")
        } else {
            return fromRestaurantModelToDto(restaurantById)
        }
    }

    override fun getDishesById(@PathVariable restaurantId: Int): GetDishesByRestaurantIdResponse {
        val list = dishService.getDishesByRestaurantId(restaurantId).map { DishDto(it.id, it.title) }
        return GetDishesByRestaurantIdResponse(list)
    }

    override fun getTablesById(@PathVariable restaurantId: Int): GetTablesByRestaurantIdResponse {
        val list = tableService.getTablesByRestaurantsId(restaurantId).map(fromModelToDto)
        return GetTablesByRestaurantIdResponse(list)
    }

    override fun createTableById(@PathVariable restaurantId: Int, @RequestBody request: CreateTableForRestaurantRequest): TableDto {
        val table = Table(0, request.title, request.numberOfPlaces)
        val createTablesForRestaurantsId = tableService.createTablesForRestaurantsId(restaurantId, table)
        return fromModelToDto(createTablesForRestaurantsId)
    }

    val fromModelToDto = { model: Table -> TableDto(model.id, model.title, model.numberOfPlaces) }
    val fromRestaurantModelToDto = { model: Restaurant -> RestaurantDto(model.id, model.title, model.imgUrl, model.description) }
}