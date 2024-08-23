package likelion.edu.vn.health_care.service.impl;

import likelion.edu.vn.health_care.entity.AppointmentEntity;
import likelion.edu.vn.health_care.entity.MedicalRecordEntity;
import likelion.edu.vn.health_care.entity.UserEntity;
import likelion.edu.vn.health_care.enumration.AppointmentStatus;
import likelion.edu.vn.health_care.model.dto.AppointmentDTO;
import likelion.edu.vn.health_care.model.dto.AppointmentDetailDTO;
import likelion.edu.vn.health_care.model.dto.Meta;
import likelion.edu.vn.health_care.model.dto.ResultPaginationDTO;
import likelion.edu.vn.health_care.model.request.AppointmentRequest;
import likelion.edu.vn.health_care.model.response.AppointmentTimeResponse;
import likelion.edu.vn.health_care.repository.AppointmentRepository;
import likelion.edu.vn.health_care.repository.MedicalRecordRepository;
import likelion.edu.vn.health_care.repository.UserRepository;
import likelion.edu.vn.health_care.security.UserInfoService;
import likelion.edu.vn.health_care.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private UserInfoService userInfoService;

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
        Page<AppointmentEntity> pageAppointment = this.appointmentRepository.findAll(pageable);
        Page<AppointmentDTO> pageAppointmentDTO = pageAppointment.map(this::convertToDTO);

        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();

        mt.setPage(pageAppointment.getNumber() + 1);
        mt.setPageSize(pageAppointment.getSize());

        mt.setPages(pageAppointment.getTotalPages());
        mt.setTotal(pageAppointment.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageAppointmentDTO.getContent());
        return rs;
    }

    @Override
    public AppointmentEntity create(AppointmentRequest appointmentRequest) {
        try {
            // Convert date and time to the appropriate format if necessary
            String appointmentDate = appointmentRequest.getAppointmentDate().toString();
            String appointmentTime = appointmentRequest.getAppointmentTime().toString();

            // Fetch the available doctor ID
            Integer availableDoctorId = appointmentRepository.findAvailableDoctorId(appointmentDate, appointmentTime)
                    .orElseThrow(() -> new UsernameNotFoundException("Don't have doctor "));

            AppointmentEntity appointment = new AppointmentEntity();
            appointment.setDoctorId(availableDoctorId);

            if (appointmentRequest.getPatientId() == 0) {
                appointment.setPatientId(userInfoService.getUserId());
            } else {
                appointment.setPatientId(appointmentRequest.getPatientId());
            }

            appointment.setAppointmentStatus(AppointmentStatus.Pending);
            appointment.setAppointmentDate(appointmentRequest.getAppointmentDate());
            appointment.setAppointmentTime(appointmentRequest.getAppointmentTime());
            return appointmentRepository.save(appointment);

        } catch (Exception e) {
            throw new RuntimeException("Error creating appointment: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AppointmentTimeResponse> getAppointmentTimeAvailable() {
        List<AppointmentTimeResponse> listAppointmentAvailable = new ArrayList<>();
        Optional<List<AppointmentTimeResponse>> listAppointmentTimeUnavailable = getAppointmentTimeUnavailable();
        
        if (listAppointmentTimeUnavailable.isPresent()) {

            listAppointmentAvailable = listAppointmentTimeUnavailable.get();

            for (AppointmentTimeResponse appointmentTimeResponse : listAppointmentAvailable) {
                System.err.println(appointmentTimeResponse);
            }
        }
        return listAppointmentAvailable;
    }

    private Optional<List<AppointmentTimeResponse>> getAppointmentTimeUnavailable() {
        return appointmentRepository.listUnavailableAppointments();
    }

    public Optional<AppointmentDetailDTO> findAppointmentDetailById(Integer id) {
        Optional<AppointmentEntity> appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isPresent()) {
            AppointmentEntity appointment = appointmentOpt.get();
            String patientName = userRepository.findById(appointment.getPatientId())
                    .map(UserEntity::getFullName)
                    .orElse("");
            String doctorName = userRepository.findById(appointment.getDoctorId())
                    .map(UserEntity::getFullName)
                    .orElse("");

            String diagnosis = "";
            String treatment = "";

            if (appointment.getMedicalRecordId() != null) {
                Optional<MedicalRecordEntity> medicalRecordOpt = medicalRecordRepository
                        .findById(appointment.getMedicalRecordId());
                if (medicalRecordOpt.isPresent()) {
                    diagnosis = medicalRecordOpt.get().getDiagnosis();
                    treatment = medicalRecordOpt.get().getTreatment();
                }
            }

            AppointmentDetailDTO dto = new AppointmentDetailDTO(
                    appointment.getId(),
                    patientName,
                    doctorName,
                    appointment.getAppointmentDate(),
                    appointment.getAppointmentTime().name(),
                    appointment.getAppointmentStatus().name(),
                    diagnosis,
                    treatment);

            return Optional.of(dto);
        } else {
            return Optional.empty();
        }
    }

    private AppointmentDTO convertToDTO(AppointmentEntity appointmentEntity) {
        String patientName = userRepository.findById(appointmentEntity.getPatientId())
                .map(UserEntity::getFullName)
                .orElse("");

        String doctorName = userRepository.findById(appointmentEntity.getDoctorId())
                .map(UserEntity::getFullName)
                .orElse("");

        return new AppointmentDTO(
                appointmentEntity.getId(),
                patientName,
                doctorName,
                appointmentEntity.getMedicalRecordId(),
                appointmentEntity.getAppointmentDate(),
                appointmentEntity.getAppointmentTime().name(),
                appointmentEntity.getAppointmentStatus().name());
    }
}
