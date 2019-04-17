package com.ak.be.engine.db.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "AK_MENU", schema = "PUBLIC", catalog = "DEFAULT")
class AkMenuEntity {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Column(name = "ID")
    var id: Int = 0
    @get:Basic
    @get:CreationTimestamp
    @get:Column(name = "CREATED_AT")
    var createdAt: Timestamp? = null
    @get:Basic
    @get:UpdateTimestamp
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as AkMenuEntity?
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
