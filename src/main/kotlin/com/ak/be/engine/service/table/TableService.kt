package com.ak.be.engine.service.table

import com.ak.be.engine.service.table.model.Table

interface TableService {

    fun getTablesByRestaurantsId(restaurantId: Int): List<Table>

    fun createTablesForRestaurantsId(restaurantId: Int, table: Table): Table
}
