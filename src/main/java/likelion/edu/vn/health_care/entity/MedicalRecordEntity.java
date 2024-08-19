package likelion.edu.vn.health_care.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Table(name = "medical_records")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordEntity extends BaseEntity{

    private String diagnosis;

    private String treatment;
}
