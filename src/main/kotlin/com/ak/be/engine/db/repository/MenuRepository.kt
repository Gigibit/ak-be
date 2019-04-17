package com.ak.be.engine.db.repository

import com.ak.be.engine.db.entity.AkMenuEntity
import org.springframework.data.repository.CrudRepository

interface MenuRepository : CrudRepository<AkMenuEntity, Int>