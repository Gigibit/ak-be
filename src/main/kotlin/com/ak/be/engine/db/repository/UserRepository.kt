package com.ak.be.engine.db.repository

import com.ak.be.engine.db.entity.AkUserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<AkUserEntity, Int> {
    fun findByEmail(email: String): AkUserEntity?
}