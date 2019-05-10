package com.ak.be.engine.controller.restaurant.dto

import com.ak.be.engine.controller.core.dto.BaseResponse

data class GetMenuByRestaurantResponse(val menuItems: List<MenuDto>) : BaseResponse()
