package com.ak.be.engine.db.repository

import com.ak.be.engine.db.entity.AkMenuOrderEntity
import org.springframework.data.repository.CrudRepository

interface MenuOrderRepository : CrudRepository<AkMenuOrderEntity, Int>