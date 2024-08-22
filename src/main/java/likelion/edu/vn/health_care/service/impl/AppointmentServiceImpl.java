package likelion.edu.vn.health_care.service.impl;

import likelion.edu.vn.health_care.entity.AppointmentEntity;
import likelion.edu.vn.health_care.entity.UserEntity;
import likelion.edu.vn.health_care.enumration.AppointmentStatus;
import likelion.edu.vn.health_care.model.dto.Meta;
import likelion.edu.vn.health_care.model.dto.ResultPaginationDTO;
import likelion.edu.vn.health_care.model.request.AppointmentRequest;
import likelion.edu.vn.health_care.repository.AppointmentRepository;
import likelion.edu.vn.health_care.service.AppointmentService;
import likelion.edu.vn.health_care.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserService userService;

    @Override
    public AppointmentEntity create(AppointmentEntity appointment) {
        try {
            System.out.println("Appointment created");

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
    public void delete(int id) {
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
    public ResultPaginationDTO handleGetAll(Pageable pageable) {
        Page<AppointmentEntity> pMedicalRecord = this.appointmentRepository.findAll(pageable);

        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();

        mt.setPage(pMedicalRecord.getNumber() + 1);
        mt.setPageSize(pMedicalRecord.getSize());

        mt.setPages(pMedicalRecord.getTotalPages());
        mt.setTotal(pMedicalRecord.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pMedicalRecord.getContent());
        return rs;
    }

    @Override
    public AppointmentEntity create(AppointmentRequest appointmentRequest) {
        try {
            // Convert date and time to the appropriate format if necessary
            String appointmentDate = appointmentRequest.getAppointmentDate().toString();
            String appointmentTime = appointmentRequest.getAppointmentTime().toString();

            // Log for debugging purposes
            System.err.println("Appointment1 created with date: " + appointmentRequest.getAppointmentDate());
            System.err.println("Appointment2 created with date: " + appointmentDate);

            System.err.println("Appointment created with time: " + appointmentTime);

            // Fetch the available doctor ID
            Optional<Integer> availableDoctorId = appointmentRepository.findAvailableDoctorId(appointmentDate, appointmentTime);
            System.err.println("Doctor ID available: " + availableDoctorId);


            if (availableDoctorId.isPresent()) {
                Optional<UserEntity> userEntity = userService.findById(availableDoctorId.get());
                System.err.println("UserEntity: " + userEntity);
                AppointmentEntity appointment = new AppointmentEntity();

                if (userEntity.isPresent()) {
                    UserEntity user = userEntity.get();
//                    appointment.setDoctor(user);
//                    appointment.setPatient(user);
                    appointment.setAppointmentStatus(AppointmentStatus.Pending);
                    appointment.setAppointmentDate(appointmentRequest.getAppointmentDate());
                    appointment.setAppointmentTime(appointmentRequest.getAppointmentTime());
                    return appointmentRepository.save(appointment);
                }


            }
            return null;

            // Create the AppointmentEntity
//            AppointmentEntity appointment = new AppointmentEntity();
//            appointment.setDoctorId(availableDoctorId);
//            appointment.setAppointmentDate(appointmentRequest.getAppointmentDate());
//            appointment.setAppointmentTime(appointmentRequest.getAppointmentTime());
            // Set other necessary fields for the appointment
            // ...

            // Save the appointment

        } catch (Exception e) {
            throw new RuntimeException("Error creating appointment: " + e.getMessage(), e);
        }
    }

}
