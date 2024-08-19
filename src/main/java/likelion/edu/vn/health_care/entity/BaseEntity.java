package likelion.edu.vn.health_care.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@MappedSuperclass
@Data
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = true, updatable = false)
    private Date createTime;

    @Column(nullable = true, insertable = false)
    private Date updateTime;

    @PrePersist
    protected void onCreate() {
        this.createTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateTime = new Date();
    }
}
