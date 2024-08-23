package likelion.edu.vn.health_care.model.response;

import java.time.LocalDate;

public interface AppointmentTimeResponse {
    LocalDate getAppointmentDate();  // Maps to 'a.appointment_date AS appointmentDate'
    String getAppointmentTime();     // Maps to 'a.appointment_time AS appointmentTime'
}