package com.ak.be.engine.controller.table.dto

import com.ak.be.engine.controller.core.dto.BaseResponse

data class TableDto(val id: Int, val title: String, val numberOfPlaces: Int)
data class GetTablesForRestaurantResponse(val tables: List<TableDto>) : BaseResponse()
