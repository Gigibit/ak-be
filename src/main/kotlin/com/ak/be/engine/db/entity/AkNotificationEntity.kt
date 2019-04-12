package com.ak.be.engine.db.entity

import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "AK_NOTIFICATION", schema = "PUBLIC", catalog = "DEFAULT")
class AkNotificationEntity {
    @get:Id
    @get:Column(name = "ID")
    var id: Int = 0
    @get:Basic
    @get:Column(name = "TITLE")
    lateinit var title: String
    @get:Basic
    @get:Column(name = "DESCRIPTION")
    var description: String? = null
    @get:Basic
    @get:Column(name = "CREATED_AT")
    lateinit var createdAt: Timestamp
    @get:Basic
    @get:Column(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null
    @get:OneToMany(mappedBy = "akNotificationByNotificationId")
    var akOrdersById: Collection<AkOrderEntity>? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as AkNotificationEntity?
        return id == that!!.id &&
                title == that.title &&
                description == that.description &&
                createdAt == that.createdAt &&
                updatedAt == that.updatedAt
    }

    override fun hashCode(): Int {
        return Objects.hash(id, title, description, createdAt, updatedAt)
    }
}
