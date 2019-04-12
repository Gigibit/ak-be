package com.ak.be.engine.db.repository

import com.ak.be.engine.db.entity.AkRestaurantEntity
import org.springframework.data.repository.CrudRepository

interface RestaurantRepository : CrudRepository<AkRestaurantEntity, Int>