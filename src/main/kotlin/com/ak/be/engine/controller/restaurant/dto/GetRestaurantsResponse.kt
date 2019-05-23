package com.ak.be.engine.controller.restaurant.dto

import com.ak.be.engine.controller.core.dto.BaseResponse

data class GetRestaurantsResponse(val restaurants: List<RestaurantDto>) : BaseResponse()
