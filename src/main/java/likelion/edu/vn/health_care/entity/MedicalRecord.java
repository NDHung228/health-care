package likelion.edu.vn.health_care.entity;

import jakarta.persistence.Entity;
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
public class MedicalRecord extends BaseEntity{

    @NonNull
    private String appointmentId;

    @NonNull
    private String diagnosis;

    @NonNull
    private String treatment;
}
