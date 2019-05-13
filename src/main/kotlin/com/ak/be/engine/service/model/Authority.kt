package com.ak.be.engine.service.model

import com.ak.be.engine.controller.user.dto.AuthorityDto
import java.io.Serializable

data class Authority(val id: Int, val name: String) : Serializable

fun Authority.toDto(): AuthorityDto {
    return AuthorityDto(this.id, this.name)
}