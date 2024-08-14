package likelion.edu.vn.health_care.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import likelion.edu.vn.health_care.enumration.AppointmentStatus;
import likelion.edu.vn.health_care.enumration.AppointmentTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Entity
@Table(name = "appointment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentEntity extends BaseEntity {

    @NonNull
    private int patient_id;

    @NonNull
    private int doctor_id;

    @NonNull
    private Date appointmentDate;

    @NonNull
    @Enumerated(EnumType.STRING)
    private AppointmentTime appointmentTime;

    @NonNull
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

}
