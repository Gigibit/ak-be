package com.ak.be.engine.db.repository

import com.ak.be.engine.db.entity.AkOrderEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface OrderRepository : CrudRepository<AkOrderEntity, Int> {
    @Query(value = "SELECT *FROM AK_ORDER INNER JOIN AK_TABLE AT on AK_ORDER.TABLE_ID = AT.ID WHERE AT.RESTAURANT_ID = :restaurantId ORDER BY AK_ORDER.CREATED_AT DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    fun findOrdersByRestaurantId(@Param("restaurantId") restaurantId: Int, @Param("limit") limit: Int, @Param("offset") offset: Int): List<AkOrderEntity>
}