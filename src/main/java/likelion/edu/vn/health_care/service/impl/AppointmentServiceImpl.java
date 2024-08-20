package likelion.edu.vn.health_care.service.impl;

import likelion.edu.vn.health_care.entity.AppointmentEntity;
import likelion.edu.vn.health_care.repository.AppointmentRepository;
import likelion.edu.vn.health_care.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public AppointmentEntity create(AppointmentEntity appointment) {
        try {
            return appointmentRepository.save(appointment);
        } catch (Exception e) {
            throw new RuntimeException("Error creating appointment: " + e.getMessage(), e);
        }
    }

    @Override
    public AppointmentEntity update(AppointmentEntity appointment) {
        try {
            Optional<AppointmentEntity> existingAppointment = appointmentRepository.findById(appointment.getId());
            if (existingAppointment.isPresent()) {
                return appointmentRepository.save(appointment);
            } else {
                throw new RuntimeException("Appointment not found with id: " + appointment.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating appointment: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(AppointmentEntity appointmentEntity) {

    }

    @Override
    public Iterable<AppointmentEntity> findAll() {
        try {
            return appointmentRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving appointments: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<AppointmentEntity> findById(Integer id) {
        try {
            return appointmentRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving appointment by id: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            if (appointmentRepository.existsById(id)) {
                appointmentRepository.deleteById(id);
            } else {
                throw new RuntimeException("Appointment not found with id: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting appointment: " + e.getMessage(), e);
        }
    }
}
