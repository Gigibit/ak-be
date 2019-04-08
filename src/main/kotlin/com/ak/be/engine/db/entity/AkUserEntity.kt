package com.ak.be.engine.db.entity

import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "AK_USER", schema = "PUBLIC", catalog = "DEFAULT")
class AkUserEntity {
    @get:Id
    @get:Column(name = "ID")
    var id: Int = 0
    @get:Basic
    @get:Column(name = "FIRST_NAME")
    var firstName: String? = null
    @get:Basic
    @get:Column(name = "LAST_NAME")
    var lastName: String? = null
    @get:Basic
    @get:Column(name = "EMAIL")
    var email: String? = null
    @get:Basic
    @get:Column(name = "CREATED_AT")
    var createdAt: Timestamp? = null
    @get:Basic
    @get:Column(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null
    @get:OneToMany(mappedBy = "akUserByUserId")
    var akOrdersById: Collection<AkOrderEntity>? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as AkUserEntity?
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
