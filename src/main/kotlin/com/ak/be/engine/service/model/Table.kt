package com.ak.be.engine.service.model

import com.ak.be.engine.controller.table.dto.TableDto

data class Table(val id: Int, val title: String, val numberOfPlaces: Int)

fun Table.toDto(): TableDto {
    return TableDto(this.id, this.title, this.numberOfPlaces)
}