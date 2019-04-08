package com.ak.be.engine.db.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "AK_MENU", schema = "PUBLIC", catalog = "DEFAULT")
public class AkMenuEntity {
    private int id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int state;
    private int type;
    private AkDishEntity akDishByDishId;
    private AkRestaurantEntity akRestaurantByRestaurantId;
    private Collection<AkOrderEntity> akOrdersById;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "STATE")
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Basic
    @Column(name = "TYPE")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AkMenuEntity that = (AkMenuEntity) o;
        return id == that.id &&
                state == that.state &&
                type == that.type &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, updatedAt, state, type);
    }

    @ManyToOne
    @JoinColumn(name = "DISH_ID", referencedColumnName = "ID", nullable = false)
    public AkDishEntity getAkDishByDishId() {
        return akDishByDishId;
    }

    public void setAkDishByDishId(AkDishEntity akDishByDishId) {
        this.akDishByDishId = akDishByDishId;
    }

    @ManyToOne
    @JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "ID", nullable = false)
    public AkRestaurantEntity getAkRestaurantByRestaurantId() {
        return akRestaurantByRestaurantId;
    }

    public void setAkRestaurantByRestaurantId(AkRestaurantEntity akRestaurantByRestaurantId) {
        this.akRestaurantByRestaurantId = akRestaurantByRestaurantId;
    }

    @OneToMany(mappedBy = "akMenuByMenuId")
    public Collection<AkOrderEntity> getAkOrdersById() {
        return akOrdersById;
    }

    public void setAkOrdersById(Collection<AkOrderEntity> akOrdersById) {
        this.akOrdersById = akOrdersById;
    }
}
