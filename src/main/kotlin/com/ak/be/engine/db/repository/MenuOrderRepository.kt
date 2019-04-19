package com.ak.be.engine.db.repository

import com.ak.be.engine.db.entity.AkMenuOrderEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface MenuOrderRepository : CrudRepository<AkMenuOrderEntity, Int> {

    @Query("SELECT AK_MENU_ORDER.* FROM AK_MENU_ORDER WHERE ORDER_ID = :orderId", nativeQuery = true)
    fun findAllByOrderId(@Param("orderId") orderId: Int): List<AkMenuOrderEntity>
}
