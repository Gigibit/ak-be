package com.ak.be.engine.db.entity

import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "AK_TABLE", schema = "PUBLIC", catalog = "DEFAULT")
class AkTableEntity {
    @get:Id
    @get:Column(name = "ID")
    var id: Int = 0
    @get:Basic
    @get:Column(name = "TITLE")
    var title: String? = null
    @get:Basic
    @get:Column(name = "CREATED_AT")
    var createdAt: Timestamp? = null
    @get:Basic
    @get:Column(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null
    @get:Basic
    @get:Column(name = "NUMBER_OF_PLACES")
    var numberOfPlaces: Int = 0
    @get:OneToMany(mappedBy = "akTableByTableId")
    var akOrdersById: Collection<AkOrderEntity>? = null
    @get:ManyToOne
    @get:JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "ID", nullable = false)
    var akRestaurantByRestaurantId: AkRestaurantEntity? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as AkTableEntity?
        return id == that!!.id &&
                numberOfPlaces == that.numberOfPlaces &&
                title == that.title &&
                createdAt == that.createdAt &&
                updatedAt == that.updatedAt
    }

    override fun hashCode(): Int {
        return Objects.hash(id, title, createdAt, updatedAt, numberOfPlaces)
    }
}
