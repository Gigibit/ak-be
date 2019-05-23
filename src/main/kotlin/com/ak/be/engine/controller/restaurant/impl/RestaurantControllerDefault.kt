package com.ak.be.engine.controller.restaurant.impl

import com.ak.be.engine.controller.restaurant.RestaurantController
import com.ak.be.engine.controller.restaurant.dto.*
import com.ak.be.engine.controller.table.dto.CreateTableForRestaurantRequest
import com.ak.be.engine.controller.table.dto.GetTablesForRestaurantResponse
import com.ak.be.engine.service.auth.AuthService
import com.ak.be.engine.service.menu.MenuService
import com.ak.be.engine.service.model.Menu
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
import javax.validation.Valid

const val DEFAULT_LIMIT = 10
const val DEFAULT_OFFSET = 0

@RestController
class RestaurantControllerDefault(val tableService: TableService,
                                  val restaurantService: RestaurantService,
                                  val orderService: OrderService,
                                  val simpMessagingTemplate: SimpMessageSendingOperations,
                                  val menuService: MenuService,
                                  val authService: AuthService) : RestaurantController {
    override fun getRestaurants(): GetRestaurantsResponse {
        val userOrFail = authService.getUserOrFail()
        val userRestaurants = restaurantService.getUserRestaurants(userOrFail)
                .map { it.toDto() }
        return GetRestaurantsResponse(userRestaurants)
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    override fun createRestaurant(@Valid @RequestBody request: CreateRestaurantRequest): CreateRestaurantResponse {
        val userOrFail = authService.getUserOrFail()
        val userRestaurants = restaurantService.getUserRestaurants(userOrFail)
        if (userRestaurants.isEmpty()) {
            throw IllegalStateException("Not allowed to create restaurant for this type of user")
        } else {
            val title = request.title
            val imgUrl = request.imgUrl
            val description = request.description
            val createRestaurant = restaurantService.createRestaurant(userOrFail, title, imgUrl, description)
            return CreateRestaurantResponse(createRestaurant.toDto())
        }
    }

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

    override fun getMenuForRestaurant(@PathVariable restaurantId: Int): GetMenuByRestaurantResponse {
        //todo should be n accessed by all without login
        val list = menuService.findMenuForRestaurantId(restaurantId).map(Menu::toDto)
        return GetMenuByRestaurantResponse(list)
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    override fun getTablesForRestaurant(@PathVariable restaurantId: Int): GetTablesForRestaurantResponse {
        val userOrFail = authService.getUserOrFail()
        restaurantService.validateUserBelongsToRestaurant(userOrFail, restaurantId)
        val list = tableService.getTablesByRestaurantsId(restaurantId).map(Table::toDto)
        return GetTablesForRestaurantResponse(list)
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    override fun createTableForRestaurant(@PathVariable restaurantId: Int, @RequestBody request: CreateTableForRestaurantRequest): CreateTableForRestaurantResponse {
        val userOrFail = authService.getUserOrFail()
        restaurantService.validateUserBelongsToRestaurant(userOrFail, restaurantId)
        val createTablesForRestaurantsId = tableService.createTablesForRestaurantsId(restaurantId, Table.new(request.title, request.numberOfPlaces))
        return CreateTableForRestaurantResponse(createTablesForRestaurantsId.toDto())
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    override fun getOrdersForRestaurant(@PathVariable restaurantId: Int, @RequestParam("limit") limit: Int?, @RequestParam("offset") offset: Int?): GetOrdersResponse {
        val userOrFail = authService.getUserOrFail()
        restaurantService.validateUserBelongsToRestaurant(userOrFail, restaurantId)
        val orders = orderService.getOrders(restaurantId, limit ?: DEFAULT_LIMIT, offset ?: DEFAULT_OFFSET)
                .map { order -> order.toDto() }
        return GetOrdersResponse(orders)
    }

}