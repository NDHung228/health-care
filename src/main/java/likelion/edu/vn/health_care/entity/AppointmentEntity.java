package likelion.edu.vn.health_care.entity;

import jakarta.persistence.*;
import likelion.edu.vn.health_care.enumration.AppointmentStatus;
import likelion.edu.vn.health_care.enumration.AppointmentTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Entity
@Table(name = "appointments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentEntity extends BaseEntity {

    @Column(name = "patient_id", nullable = false)
    private Integer patientId;

    @Column(name = "doctor_id", nullable = false)
    private Integer doctorId;

    @Column(name = "medical_record_id")
    private Integer medicalRecordId;

    @NonNull
    private Date appointmentDate;

    @NonNull
    @Enumerated(EnumType.STRING)
    private AppointmentTime appointmentTime;

    @NonNull
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;
}
