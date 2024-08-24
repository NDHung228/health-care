package likelion.edu.vn.health_care.controller;

import likelion.edu.vn.health_care.entity.AppointmentEntity;
import likelion.edu.vn.health_care.entity.MedicalRecordEntity;
import likelion.edu.vn.health_care.enumration.AppointmentTime;
import likelion.edu.vn.health_care.model.dto.AppointmentDetailDTO;
import likelion.edu.vn.health_care.model.dto.ResultPaginationDTO;
import likelion.edu.vn.health_care.model.request.AppointmentRequest;
import likelion.edu.vn.health_care.model.response.AppointmentTimeResponse;
import likelion.edu.vn.health_care.service.AppointmentService;
import likelion.edu.vn.health_care.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Object> createAppointment(@RequestBody AppointmentRequest appointment) {
        System.err.println("This here");

        try {

            AppointmentEntity createdAppointment = appointmentService.create(appointment);
            return ResponseHandler.generateResponse(HttpStatus.CREATED, false, "Appointment created successfully",
                    createdAppointment);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllAppointments(
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) AppointmentTime time,
            Pageable pageable) {
        try {
            ResultPaginationDTO result = this.appointmentService.handleGetAllAppointments(patientName, date, time, pageable);
            return ResponseHandler.generateResponse(HttpStatus.OK, false, "Records retrieved successfully", result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, e.getMessage(), null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAppointmentById(@PathVariable Integer id) {
        try {
            Optional<AppointmentDetailDTO> appointment = appointmentService.findAppointmentDetailById(id);
            if (appointment.isPresent()) {
                return ResponseHandler.generateResponse(HttpStatus.OK, false, "Appointment found", appointment.get());
            } else {
                return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, true, "Appointment not found", null);
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAppointment(@PathVariable Integer id,
            @RequestBody AppointmentEntity appointment) {
        try {
            appointment.setId(id);
            AppointmentEntity updatedAppointment = appointmentService.update(appointment);
            return ResponseHandler.generateResponse(HttpStatus.OK, false, "Appointment updated successfully",
                    updatedAppointment);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAppointment(@PathVariable Integer id) {
        try {
            appointmentService.delete(id);
            return ResponseHandler.generateResponse(HttpStatus.OK, false, "Appointment deleted successfully", null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null);
        }
    }

    @GetMapping("/appointment-available")
    public ResponseEntity<Object> getAppointmentAvailable() {
        try {

            List<AppointmentTimeResponse> listAppointmentTime = appointmentService.getAppointmentTimeAvailable();
            return ResponseHandler.generateResponse(HttpStatus.OK, false, "Appointment found", listAppointmentTime);

        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null);
        }
    }
}
