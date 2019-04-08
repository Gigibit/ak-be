package com.ak.be.engine.db.repository

import com.ak.be.engine.db.entity.AkDishEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface DishRepository : CrudRepository<AkDishEntity, Int> {
    fun findAkDishEntityById(id: Int): AkDishEntity

    @Query(value = "SELECT AK_DISH.*FROM AK_DISH INNER JOIN AK_MENU AM on AK_DISH.ID = AM.DISH_ID INNER JOIN AK_RESTAURANT AR on AM.RESTAURANT_ID = AR.ID WHERE RESTAURANT_ID = :restaurantId", nativeQuery = true)
    fun findDishesByRestaurantId(@Param("restaurantId") restaurantId: Int): List<AkDishEntity>
}