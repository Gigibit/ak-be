package com.ak.be.engine.db.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "AK_RESTAURANT", schema = "PUBLIC", catalog = "DEFAULT")
class AkRestaurantEntity {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Column(name = "ID")
    var id: Int = 0
    @get:Basic
    @get:Column(name = "TITLE")
    lateinit var title: String
    @get:Basic
    @get:Column(name = "IMG_URL")
    var imgUrl: String? = null
    @get:Basic
    @get:Column(name = "DESCRIPTION")
    var description: String? = null
    @get:Basic
    @get:CreationTimestamp
    @get:Column(name = "CREATED_AT")
    var createdAt: Timestamp? = null
    @get:Basic
    @get:UpdateTimestamp
    @get:Column(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null
    @get:OneToMany(mappedBy = "akRestaurantByRestaurantId")
    var akMenusById: Collection<AkMenuEntity>? = null
    @get:OneToMany(mappedBy = "akRestaurantByRestaurantId")
    var akTablesById: Collection<AkTableEntity>? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as AkRestaurantEntity?
        return id == that!!.id &&
                title == that.title &&
                imgUrl == that.imgUrl &&
                description == that.description &&
                createdAt == that.createdAt &&
                updatedAt == that.updatedAt
    }

    override fun hashCode(): Int {
        return Objects.hash(id, title, imgUrl, description, createdAt, updatedAt)
    }
}
