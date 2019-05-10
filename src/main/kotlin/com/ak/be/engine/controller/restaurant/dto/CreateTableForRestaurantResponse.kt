package com.ak.be.engine.controller.restaurant.dto

import com.ak.be.engine.controller.core.dto.BaseResponse
import com.ak.be.engine.controller.table.dto.TableDto

data class CreateTableForRestaurantResponse(val table: TableDto) : BaseResponse()