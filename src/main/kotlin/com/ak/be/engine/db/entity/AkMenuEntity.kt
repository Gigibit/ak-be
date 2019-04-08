package com.ak.be.engine.db.entity

import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "AK_MENU", schema = "PUBLIC", catalog = "DEFAULT")
class AkMenuEntity {
    @get:Id
    @get:Column(name = "ID")
    var id: Int = 0
    @get:Basic
    @get:Column(name = "CREATED_AT")
    var createdAt: Timestamp? = null
    @get:Basic
    @get:Column(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null
    @get:Basic
    @get:Column(name = "STATE")
    var state: Int = 0
    @get:Basic
    @get:Column(name = "TYPE")
    var type: Int = 0
    @get:ManyToOne
    @get:JoinColumn(name = "DISH_ID", referencedColumnName = "ID", nullable = false)
    var akDishByDishId: AkDishEntity? = null
    @get:ManyToOne
    @get:JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "ID", nullable = false)
    var akRestaurantByRestaurantId: AkRestaurantEntity? = null
    @get:OneToMany(mappedBy = "akMenuByMenuId")
    var akOrdersById: Collection<AkOrderEntity>? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as AkMenuEntity?
        return id == that!!.id &&
                state == that.state &&
                type == that.type &&
                createdAt == that.createdAt &&
                updatedAt == that.updatedAt
    }

    override fun hashCode(): Int {
        return Objects.hash(id, createdAt, updatedAt, state, type)
    }
}
