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

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private UserEntity patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private UserEntity doctor;

    @OneToOne
    @JoinColumn(name = "medical_record_id")
    private MedicalRecordEntity medicalRecord;

    @NonNull
    private Date appointmentDate;

    @NonNull
    @Enumerated(EnumType.STRING)
    private AppointmentTime appointmentTime;

    @NonNull
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;
}
