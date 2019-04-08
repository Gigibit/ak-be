package com.ak.be.engine.db.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "AK_RESTAURANT", schema = "PUBLIC", catalog = "DEFAULT")
public class AkRestaurantEntity {
    private int id;
    private String title;
    private String imgUrl;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Collection<AkMenuEntity> akMenusById;
    private Collection<AkTableEntity> akTablesById;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "IMG_URL")
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Basic
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "CREATED_AT")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "UPDATED_AT")
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AkRestaurantEntity that = (AkRestaurantEntity) o;
        return id == that.id &&
                Objects.equals(title, that.title) &&
                Objects.equals(imgUrl, that.imgUrl) &&
                Objects.equals(description, that.description) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, imgUrl, description, createdAt, updatedAt);
    }

    @OneToMany(mappedBy = "akRestaurantByRestaurantId")
    public Collection<AkMenuEntity> getAkMenusById() {
        return akMenusById;
    }

    public void setAkMenusById(Collection<AkMenuEntity> akMenusById) {
        this.akMenusById = akMenusById;
    }

    @OneToMany(mappedBy = "akRestaurantByRestaurantId")
    public Collection<AkTableEntity> getAkTablesById() {
        return akTablesById;
    }

    public void setAkTablesById(Collection<AkTableEntity> akTablesById) {
        this.akTablesById = akTablesById;
    }
}
