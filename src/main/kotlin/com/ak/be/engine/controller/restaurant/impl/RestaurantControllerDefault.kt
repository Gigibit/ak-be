package com.ak.be.engine.controller.restaurant.impl

import com.ak.be.engine.controller.dish.dto.DishDto
import com.ak.be.engine.controller.dish.dto.GetDishesByRestaurantIdResponse
import com.ak.be.engine.controller.restaurant.RestaurantController
import com.ak.be.engine.controller.restaurant.dto.*
import com.ak.be.engine.controller.table.dto.CreateTableForRestaurantRequest
import com.ak.be.engine.controller.table.dto.GetTablesByRestaurantIdResponse
import com.ak.be.engine.controller.table.dto.TableDto
import com.ak.be.engine.service.dish.DishService
import com.ak.be.engine.service.order.OrderService
import com.ak.be.engine.service.order.model.Notification
import com.ak.be.engine.service.order.model.User
import com.ak.be.engine.service.restaurant.RestaurantService
import com.ak.be.engine.service.restaurant.model.Restaurant
import com.ak.be.engine.service.table.TableService
import com.ak.be.engine.service.table.model.Table
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

const val DEFAULT_LIMIT = 10
const val DEFAULT_OFFSET = 0

@RestController
class RestaurantControllerDefault(@Autowired val dishService: DishService,
                                  @Autowired val tableService: TableService,
                                  @Autowired val restaurantService: RestaurantService,
                                  @Autowired val orderService: OrderService) : RestaurantController {

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

    override fun getOrders(@PathVariable restaurantId: Int, @RequestParam("limit") limit: Int?, @RequestParam("offset") offset: Int?): GetOrdersResponse {
        val orders = orderService.getOrders(restaurantId, limit ?: DEFAULT_LIMIT, offset ?: DEFAULT_OFFSET)
                .map {
                    val userDto = it.user?.let { u: User -> fromUserModelToDto(u) }
                    val tableDto = it.table?.let { u: Table -> fromTableModelToDto(u) }
                    val notificationDto = it.notification?.let { n: Notification -> fromNotificationModelToDto(n) }
                    OrderDto(it.id, it.createdAt, userDto, tableDto, notificationDto)
                }
        return GetOrdersResponse(orders)
    }

    val fromModelToDto = { model: Table -> TableDto(model.id, model.title, model.numberOfPlaces) }
    val fromRestaurantModelToDto = { model: Restaurant -> RestaurantDto(model.id, model.title, model.imgUrl, model.description) }
    val fromUserModelToDto = { model: User -> UserDto(model.id, model.firstName, model.lastName) }
    val fromTableModelToDto = { model: Table -> TableDto(model.id, model.title, model.numberOfPlaces) }
    val fromNotificationModelToDto = { model: Notification -> NotificationDto(model.id, model.title, model.createdAt, model.description) }
}