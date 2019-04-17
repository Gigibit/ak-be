package com.ak.be.engine.db.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "AK_USER", schema = "PUBLIC", catalog = "DEFAULT")
class AkUserEntity {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Column(name = "ID")
    var id: Int = 0
    @get:Basic
    @get:Column(name = "FIRST_NAME")
    lateinit var firstName: String
    @get:Basic
    @get:Column(name = "LAST_NAME")
    lateinit var lastName: String
    @get:Basic
    @get:Column(name = "EMAIL")
    lateinit var email: String
    @get:Basic
    @get:CreationTimestamp
    @get:Column(name = "CREATED_AT")
    var createdAt: Timestamp? = null
    @get:Basic
    @get:UpdateTimestamp
    @get:Column(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null
    @get:OneToMany(mappedBy = "akUserByUserId")
    var akOrdersById: Collection<AkOrderEntity>? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as AkUserEntity?
        return id == that!!.id &&
                firstName == that.firstName &&
                lastName == that.lastName &&
                email == that.email &&
                createdAt == that.createdAt &&
                updatedAt == that.updatedAt
    }

    override fun hashCode(): Int {
        return Objects.hash(id, firstName, lastName, email, createdAt, updatedAt)
    }
}
