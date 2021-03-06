package com.ak.be.engine.service.model

import com.ak.be.engine.controller.restaurant.dto.UserDto
import com.ak.be.engine.db.entity.AkUserEntity
import java.io.Serializable

data class User(val id: Int,
                val email: String,
                val passwordHash: String,
                val firstName: String,
                val lastName: String,
                val authorities: List<Authority>,
                val accountExpired: Boolean,
                val accountLocked: Boolean,
                val credentialsExpired: Boolean,
                val enabled: Boolean) : Serializable {
    companion object
}

fun User.toDto(): UserDto {
    return UserDto(this.id, this.firstName, this.lastName)
}

fun User.Companion.fromEntity(userEntity: AkUserEntity): User {
    val authorities = userEntity.authorities?.map { Authority(it.id, it.name) } ?: ArrayList()
    return User(userEntity.id,
            userEntity.email,
            userEntity.passwordHash,
            userEntity.firstName,
            userEntity.lastName,
            authorities,
            userEntity.accountExpired,
            userEntity.accountLocked,
            userEntity.credentialsExpired,
            userEntity.enabled
    )
}