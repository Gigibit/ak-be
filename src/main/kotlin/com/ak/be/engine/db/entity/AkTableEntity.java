package com.ak.be.engine.db.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "AK_TABLE", schema = "PUBLIC", catalog = "DEFAULT")
public class AkTableEntity {
    private int id;
    private String title;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int numberOfPlaces;
    private Collection<AkOrderEntity> akOrdersById;
    private AkRestaurantEntity akRestaurantByRestaurantId;

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

    @Basic
    @Column(name = "NUMBER_OF_PLACES")
    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public void setNumberOfPlaces(int numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AkTableEntity that = (AkTableEntity) o;
        return id == that.id &&
                numberOfPlaces == that.numberOfPlaces &&
                Objects.equals(title, that.title) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, createdAt, updatedAt, numberOfPlaces);
    }

    @OneToMany(mappedBy = "akTableByTableId")
    public Collection<AkOrderEntity> getAkOrdersById() {
        return akOrdersById;
    }

    public void setAkOrdersById(Collection<AkOrderEntity> akOrdersById) {
        this.akOrdersById = akOrdersById;
    }

    @ManyToOne
    @JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "ID", nullable = false)
    public AkRestaurantEntity getAkRestaurantByRestaurantId() {
        return akRestaurantByRestaurantId;
    }

    public void setAkRestaurantByRestaurantId(AkRestaurantEntity akRestaurantByRestaurantId) {
        this.akRestaurantByRestaurantId = akRestaurantByRestaurantId;
    }
}
