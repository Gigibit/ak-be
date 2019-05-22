package com.ak.be.engine.db.repository

import com.ak.be.engine.db.entity.AkAuthorityEntity
import org.springframework.data.repository.CrudRepository

interface AuthorityRepository : CrudRepository<AkAuthorityEntity, Int>