package com.ak.be.engine.controller.restaurant.dto

import com.ak.be.engine.controller.core.dto.BaseRequest
import javax.validation.constraints.NotEmpty

data class CreateRestaurantRequest(@NotEmpty val title: String, val imgUrl: String?, val description: String?) : BaseRequest()

