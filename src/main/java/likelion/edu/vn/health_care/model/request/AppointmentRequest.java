package likelion.edu.vn.health_care.model.request;

import likelion.edu.vn.health_care.enumration.AppointmentTime;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentRequest {
    private Date appointmentDate;
    private AppointmentTime appointmentTime;
}
