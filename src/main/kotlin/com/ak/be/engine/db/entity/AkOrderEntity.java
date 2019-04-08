package com.ak.be.engine.db.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "AK_ORDER", schema = "PUBLIC", catalog = "DEFAULT")
public class AkOrderEntity {
    private int id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private AkMenuEntity akMenuByMenuId;
    private AkUserEntity akUserByUserId;
    private AkTableEntity akTableByTableId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AkOrderEntity that = (AkOrderEntity) o;
        return id == that.id &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, updatedAt);
    }

    @ManyToOne
    @JoinColumn(name = "MENU_ID", referencedColumnName = "ID", nullable = false)
    public AkMenuEntity getAkMenuByMenuId() {
        return akMenuByMenuId;
    }

    public void setAkMenuByMenuId(AkMenuEntity akMenuByMenuId) {
        this.akMenuByMenuId = akMenuByMenuId;
    }

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    public AkUserEntity getAkUserByUserId() {
        return akUserByUserId;
    }

    public void setAkUserByUserId(AkUserEntity akUserByUserId) {
        this.akUserByUserId = akUserByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "TABLE_ID", referencedColumnName = "ID")
    public AkTableEntity getAkTableByTableId() {
        return akTableByTableId;
    }

    public void setAkTableByTableId(AkTableEntity akTableByTableId) {
        this.akTableByTableId = akTableByTableId;
    }
}
