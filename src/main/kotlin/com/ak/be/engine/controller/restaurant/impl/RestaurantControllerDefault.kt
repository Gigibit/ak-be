package com.ak.be.engine.controller.restaurant.impl

import com.ak.be.engine.controller.dish.dto.GetDishesByRestaurantIdResponse
import com.ak.be.engine.controller.restaurant.RestaurantController
import com.ak.be.engine.controller.restaurant.dto.CreateTableForRestaurantResponse
import com.ak.be.engine.controller.restaurant.dto.GetOrdersResponse
import com.ak.be.engine.controller.restaurant.dto.RestaurantDto
import com.ak.be.engine.controller.table.dto.CreateTableForRestaurantRequest
import com.ak.be.engine.controller.table.dto.GetTablesByRestaurantIdResponse
import com.ak.be.engine.service.auth.AuthService
import com.ak.be.engine.service.dish.DishService
import com.ak.be.engine.service.model.Dish
import com.ak.be.engine.service.model.Table
import com.ak.be.engine.service.model.toDto
import com.ak.be.engine.service.order.OrderService
import com.ak.be.engine.service.restaurant.RestaurantService
import com.ak.be.engine.service.table.TableService
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.HtmlUtils

const val DEFAULT_LIMIT = 10
const val DEFAULT_OFFSET = 0

@RestController
class RestaurantControllerDefault(val dishService: DishService,
                                  val tableService: TableService,
                                  val restaurantService: RestaurantService,
                                  val orderService: OrderService,
                                  val simpMessagingTemplate: SimpMessageSendingOperations,
                                  val authService: AuthService) : RestaurantController {

    override fun getRestaurantById(@PathVariable restaurantId: Int): RestaurantDto {
        val userOrFail = authService.getUserOrFail()

        simpMessagingTemplate.convertAndSendToUser(userOrFail.email, "/topic/orders", "{\"content\":\"Hello, " + HtmlUtils.htmlEscape(userOrFail.email) + "\"}")
        simpMessagingTemplate.convertAndSendToUser("dan", "/topic/orders", "{\"content\":\"Hello, " + HtmlUtils.htmlEscape("dan") + "\"}")

        val restaurantById = restaurantService.getRestaurantById(restaurantId)
        if (restaurantById == null) {
            throw IllegalArgumentException("not found")
        } else {
            return restaurantById.toDto()

        }
    }

    override fun getDishesById(@PathVariable restaurantId: Int): GetDishesByRestaurantIdResponse {
        val list = dishService.getDishesByRestaurantId(restaurantId).map(Dish::toDto)
        return GetDishesByRestaurantIdResponse(list)
    }

    override fun getTablesById(@PathVariable restaurantId: Int): GetTablesByRestaurantIdResponse {
        val list = tableService.getTablesByRestaurantsId(restaurantId).map(Table::toDto)
        return GetTablesByRestaurantIdResponse(list)
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    override fun createTableForRestaurant(@PathVariable restaurantId: Int, @RequestBody request: CreateTableForRestaurantRequest): CreateTableForRestaurantResponse {
        val userOrFail = authService.getUserOrFail()
        restaurantService.validateUserBelongsToRestaurant(userOrFail, restaurantId)
        val createTablesForRestaurantsId = tableService.createTablesForRestaurantsId(restaurantId, Table.new(request.title, request.numberOfPlaces))
        return CreateTableForRestaurantResponse(createTablesForRestaurantsId.toDto())
    }

    override fun getOrders(@PathVariable restaurantId: Int, @RequestParam("limit") limit: Int?, @RequestParam("offset") offset: Int?): GetOrdersResponse {
        val orders = orderService.getOrders(restaurantId, limit ?: DEFAULT_LIMIT, offset ?: DEFAULT_OFFSET)
                .map { order -> order.toDto() }
        return GetOrdersResponse(orders)
    }

}