package com.ak.be.engine.controller.table.dto

data class TableDto(val id: Int, val title: String, val numberOfPlaces: Int)
data class GetTablesByRestaurantIdResponse(val tables: List<TableDto>)
