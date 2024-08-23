package likelion.edu.vn.health_care.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDetailDTO {
    private int id;
    private String patientName;
    private String doctorName;
    private LocalDate appointmentDate;
    private String appointmentTime;
    private String appointmentStatus;
    private String diagnosis;
    private String treatment;
}
