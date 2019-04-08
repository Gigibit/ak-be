package com.ak.be.engine.db.entity

import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "AK_DISH", schema = "PUBLIC", catalog = "DEFAULT")
class AkDishEntity {
    @get:Id
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
    @get:Column(name = "CREATED_AT")
    var createdAt: Timestamp? = null
    @get:Basic
    @get:Column(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null
    @get:OneToMany(mappedBy = "akDishByDishId")
    var akMenusById: Collection<AkMenuEntity>? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as AkDishEntity?
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
