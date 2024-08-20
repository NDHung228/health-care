package likelion.edu.vn.health_care.entity;

import jakarta.persistence.*;
import likelion.edu.vn.health_care.security.SecurityUtils;
import lombok.Data;

import java.util.Date;
import java.util.Optional;

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

    private String created_by;

    private String updated_by;

    @PrePersist
    protected void onCreate() {
        Optional<String> emailOptional = Optional.ofNullable(SecurityUtils.getEmail());
        this.created_by = emailOptional.orElse("");

        this.createTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        Optional<String> emailOptional = Optional.ofNullable(SecurityUtils.getEmail());
        this.updated_by = emailOptional.orElse("");
        
        this.updateTime = new Date();
    }
}
