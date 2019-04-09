package com.ak.be.engine.db.repository

import com.ak.be.engine.db.entity.AkTableEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface TableRepository : CrudRepository<AkTableEntity, Int> {
    @Query(value = "SELECT AK_TABLE.*FROM AK_TABLE WHERE AK_TABLE.RESTAURANT_ID = :restaurantId", nativeQuery = true)
    fun findTablesByRestaurantId(@Param("restaurantId") restaurantId: Int): List<AkTableEntity>
}