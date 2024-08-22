package likelion.edu.vn.health_care.controller;

import likelion.edu.vn.health_care.entity.AppointmentEntity;
import likelion.edu.vn.health_care.entity.MedicalRecordEntity;
import likelion.edu.vn.health_care.model.request.AppointmentRequest;
import likelion.edu.vn.health_care.service.AppointmentService;
import likelion.edu.vn.health_care.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Object> createAppointment(@RequestBody AppointmentRequest appointment) {
        try {
            AppointmentEntity createdAppointment = appointmentService.create(appointment);
            return ResponseHandler.generateResponse(HttpStatus.CREATED, false, "Appointment created successfully", createdAppointment);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllAppointments(
            @RequestParam("current") Optional<String> currentOptional,
            @RequestParam("pageSize") Optional<String> pageSizeOptional) {
        try {
            int current = currentOptional.filter(s -> !s.isEmpty()).map(Integer::parseInt).orElse(1);
            int pageSize = pageSizeOptional.filter(s -> !s.isEmpty()).map(Integer::parseInt).orElse(10);

            Pageable pageable = PageRequest.of(current - 1, pageSize);
            return ResponseHandler.generateResponse(HttpStatus.OK, false, "Records retrieved successfully", this.appointmentService.handleGetAll(pageable));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, e.getMessage(), null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAppointmentById(@PathVariable Integer id) {
        try {
            Optional<AppointmentEntity> appointment = appointmentService.findById(id);
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
    public ResponseEntity<Object> updateAppointment(@PathVariable Integer id, @RequestBody AppointmentEntity appointment) {
        try {
            appointment.setId(id);
            AppointmentEntity updatedAppointment = appointmentService.update(appointment);
            return ResponseHandler.generateResponse(HttpStatus.OK, false, "Appointment updated successfully", updatedAppointment);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAppointment(@PathVariable Integer id) {
        try {
            appointmentService.deleteById(id);
            return ResponseHandler.generateResponse(HttpStatus.OK, false, "Appointment deleted successfully", null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null);
        }
    }
}
