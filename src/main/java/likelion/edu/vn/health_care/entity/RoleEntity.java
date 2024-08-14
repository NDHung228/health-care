package likelion.edu.vn.health_care.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import likelion.edu.vn.health_care.enumration.Authorities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity extends BaseEntity {

    @NonNull
    @Enumerated(EnumType.STRING)
    private Authorities name;
}
