package likelion.edu.vn.health_care.model.response;

import likelion.edu.vn.health_care.enumration.AppointmentTime;

import java.time.LocalDate;

public interface AppointmentTimeResponse {
    LocalDate getAppointmentDate();  // Maps to 'a.appointment_date AS appointmentDate'
    AppointmentTime getAppointmentTime();   // Maps to 'a.appointment_time AS appointmentTime'
}