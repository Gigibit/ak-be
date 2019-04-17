package com.ak.be.engine.controller.restaurant.impl

import com.ak.be.engine.controller.Authenticatable
import com.ak.be.engine.controller.dish.dto.DishDto
import com.ak.be.engine.controller.dish.dto.GetDishesByRestaurantIdResponse
import com.ak.be.engine.controller.restaurant.RestaurantController
import com.ak.be.engine.controller.restaurant.dto.GetOrdersResponse
import com.ak.be.engine.controller.restaurant.dto.RestaurantDto
import com.ak.be.engine.controller.table.dto.CreateTableForRestaurantRequest
import com.ak.be.engine.controller.table.dto.GetTablesByRestaurantIdResponse
import com.ak.be.engine.controller.table.dto.TableDto
import com.ak.be.engine.service.dish.DishService
import com.ak.be.engine.service.model.Table
import com.ak.be.engine.service.model.toDto
import com.ak.be.engine.service.order.OrderService
import com.ak.be.engine.service.restaurant.RestaurantService
import com.ak.be.engine.service.table.TableService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.HtmlUtils

const val DEFAULT_LIMIT = 10
const val DEFAULT_OFFSET = 0

@RestController
class RestaurantControllerDefault(@Autowired val dishService: DishService,
                                  @Autowired val tableService: TableService,
                                  @Autowired val restaurantService: RestaurantService,
                                  @Autowired val orderService: OrderService,
                                  @Autowired val simpMessagingTemplate: SimpMessageSendingOperations) : RestaurantController, Authenticatable {

    override fun getRestaurantById(@PathVariable restaurantId: Int): RestaurantDto {
        val authentication = getAuthentication()
        if (authentication != null) {
            simpMessagingTemplate.convertAndSendToUser(authentication.name, "/topic/orders", "{\"content\":\"Hello, " + HtmlUtils.htmlEscape(authentication.name) + "\"}")
            simpMessagingTemplate.convertAndSendToUser("dan", "/topic/orders", "{\"content\":\"Hello, " + HtmlUtils.htmlEscape("dan") + "\"}")

        }
        val restaurantById = restaurantService.getRestaurantById(restaurantId)
        if (restaurantById == null) {
            throw IllegalArgumentException("not found")
        } else {
            return restaurantById.toDto()
        }
    }

    override fun getDishesById(@PathVariable restaurantId: Int): GetDishesByRestaurantIdResponse {
        val list = dishService.getDishesByRestaurantId(restaurantId).map { DishDto(it.id, it.title) }
        return GetDishesByRestaurantIdResponse(list)
    }

    override fun getTablesById(@PathVariable restaurantId: Int): GetTablesByRestaurantIdResponse {
        val list = tableService.getTablesByRestaurantsId(restaurantId).map { table -> table.toDto() }
        return GetTablesByRestaurantIdResponse(list)
    }

    override fun createTableById(@PathVariable restaurantId: Int, @RequestBody request: CreateTableForRestaurantRequest): TableDto {
        val table = Table(0, request.title, request.numberOfPlaces)
        val createTablesForRestaurantsId = tableService.createTablesForRestaurantsId(restaurantId, table)
        return createTablesForRestaurantsId.toDto()
    }

    override fun getOrders(@PathVariable restaurantId: Int, @RequestParam("limit") limit: Int?, @RequestParam("offset") offset: Int?): GetOrdersResponse {
        val orders = orderService.getOrders(restaurantId, limit ?: DEFAULT_LIMIT, offset ?: DEFAULT_OFFSET)
                .map { order -> order.toDto() }
        return GetOrdersResponse(orders)
    }

}