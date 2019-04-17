package com.ak.be.engine.db.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "AK_ORDER", schema = "PUBLIC", catalog = "DEFAULT")
class AkOrderEntity {
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
    @get:ManyToOne(cascade = [CascadeType.ALL])
    @get:JoinColumn(name = "NOTIFICATION_ID", referencedColumnName = "ID")
    var akNotificationByNotificationId: AkNotificationEntity? = null

    @get:ManyToOne
    @get:JoinColumn(name = "MENU_ID", referencedColumnName = "ID", nullable = false)
    var akMenuByMenuId: AkMenuEntity? = null
    @get:ManyToOne
    @get:JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    var akUserByUserId: AkUserEntity? = null
    @get:ManyToOne
    @get:JoinColumn(name = "TABLE_ID", referencedColumnName = "ID")
    var akTableByTableId: AkTableEntity? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as AkOrderEntity?
        return id == that!!.id &&
                createdAt == that.createdAt &&
                updatedAt == that.updatedAt
    }

    override fun hashCode(): Int {
        return Objects.hash(id, createdAt, updatedAt)
    }
}
