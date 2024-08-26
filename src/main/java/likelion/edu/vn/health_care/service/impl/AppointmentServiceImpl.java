package likelion.edu.vn.health_care.service.impl;

import likelion.edu.vn.health_care.entity.AppointmentEntity;
import likelion.edu.vn.health_care.entity.MedicalRecordEntity;
import likelion.edu.vn.health_care.entity.UserEntity;
import likelion.edu.vn.health_care.enumration.AppointmentStatus;
import likelion.edu.vn.health_care.enumration.AppointmentTime;
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
import likelion.edu.vn.health_care.util.SpecificationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            Integer availableDoctorId = appointmentRepository.findAvailableDoctorId(appointmentDate, appointmentTime).orElseThrow(() -> new UsernameNotFoundException("Don't have doctor "));

            AppointmentEntity appointment = new AppointmentEntity();
            appointment.setDoctorId(availableDoctorId);

            int patientId;
            // check patientId transmission by admin or jwt
            if (appointmentRequest.getPatientId() == 0)
                patientId = userInfoService.getUserId();
            else
                patientId = appointmentRequest.getPatientId();

            // Check if the patient is scheduled in pending (prevent spam)
            if (appointmentRepository.checkPatientOnPending(userInfoService.getUserId()).orElse(false)) {
                throw new RuntimeException("The patient currently has another appointment pending.");
            }

            appointment.setPatientId(patientId);
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
        List<AppointmentTimeResponse> listAppointmentAvailable = generateNext3DaysAppointments();
//        Optional<List<AppointmentTimeResponse>> listAppointmentTimeUnavailable = getAppointmentTimeUnavailable();
        List<AppointmentTimeResponse> result = new ArrayList<>();

        for (AppointmentTimeResponse appointmentTimeResponse : listAppointmentAvailable) {
            Optional<Integer> doctorIdAvailable =  appointmentRepository.findAvailableDoctorId(
                    appointmentTimeResponse.getAppointmentDate().toString(), appointmentTimeResponse.getAppointmentTime().toString());

            if (doctorIdAvailable.isPresent()) {
                result.add(appointmentTimeResponse);
            }
        }
//
//        if (listAppointmentTimeUnavailable.isPresent()) {
//            List<AppointmentTimeResponse> unavailableList = listAppointmentTimeUnavailable.get();
//            // Create a new list to store the available appointments after removing the unavailable ones
//            List<AppointmentTimeResponse> result = new ArrayList<>(listAppointmentAvailable);
//
//            // Manually remove unavailable appointments
//            for (AppointmentTimeResponse unavailable : unavailableList) {
//                result.removeIf(available -> available.getAppointmentTime().equals(unavailable.getAppointmentTime()) && available.getAppointmentDate().equals(unavailable.getAppointmentDate()));
//            }
//
//            return result;
//        }

        return result;
    }


    @Override
    public ResultPaginationDTO getAppointmentByPatientId(Pageable pageable) {
        int userId = userInfoService.getUserId();
        List<AppointmentDetailDTO> appointmentDetailDTOS = new ArrayList<>();

        // Fetch the appointments with pagination
        Page<AppointmentEntity> appointmentsPage = appointmentRepository.findByPatientId(userId, pageable);

        // Iterate through the appointments and convert them to DTOs
        appointmentsPage.getContent().forEach(appointmentEntity -> {
            String patientName = userRepository.findById(appointmentEntity.getPatientId()).map(UserEntity::getFullName).orElse("");
            String doctorName = userRepository.findById(appointmentEntity.getDoctorId()).map(UserEntity::getFullName).orElse("");

            String diagnosis = "";
            String treatment = "";

            if (appointmentEntity.getMedicalRecordId() != null) {
                Optional<MedicalRecordEntity> medicalRecordOpt = medicalRecordRepository.findById(appointmentEntity.getMedicalRecordId());
                if (medicalRecordOpt.isPresent()) {
                    diagnosis = medicalRecordOpt.get().getDiagnosis();
                    treatment = medicalRecordOpt.get().getTreatment();
                }
            }

            // Create the DTO and add it to the list
            AppointmentDetailDTO dto = new AppointmentDetailDTO(appointmentEntity.getId(), patientName, doctorName, appointmentEntity.getAppointmentDate(), appointmentEntity.getAppointmentTime().name(), appointmentEntity.getAppointmentStatus().name(), diagnosis, treatment);
            appointmentDetailDTOS.add(dto);
        });

        Meta meta = new Meta();
        meta.setPage(appointmentsPage.getNumber() + 1);
        meta.setPageSize(appointmentsPage.getSize());
        meta.setPages(appointmentsPage.getTotalPages());
        meta.setTotal(appointmentsPage.getTotalElements());

        // Create the ResultPaginationDTO and set the meta and result
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(appointmentDetailDTOS);

        return resultPaginationDTO;
    }

    @Override
    public AppointmentEntity updateAppointment(AppointmentDetailDTO appointmentDetailDTO) {
        try {
            // Find existing appointment
            Optional<AppointmentEntity> existingAppointmentOpt = appointmentRepository.findById(appointmentDetailDTO.getId());
            if (existingAppointmentOpt.isPresent()) {
                AppointmentEntity existingAppointment = existingAppointmentOpt.get();

                // Update appointment details
                existingAppointment.setAppointmentDate(appointmentDetailDTO.getAppointmentDate());
                existingAppointment.setAppointmentTime(AppointmentTime.valueOf(appointmentDetailDTO.getAppointmentTime()));
                existingAppointment.setAppointmentStatus(AppointmentStatus.valueOf(appointmentDetailDTO.getAppointmentStatus()));

                // Update or create medical record
                MedicalRecordEntity medicalRecord;

                if (existingAppointment.getMedicalRecordId() != null) {
                    // Update existing medical record
                    Optional<MedicalRecordEntity> medicalRecordOpt = medicalRecordRepository.findById(existingAppointment.getMedicalRecordId());
                    if (medicalRecordOpt.isPresent()) {
                        medicalRecord = medicalRecordOpt.get();
                    } else {
                        throw new RuntimeException("Medical record not found with id: " + existingAppointment.getMedicalRecordId());
                    }
                } else {
                    // Create new medical record
                    medicalRecord = new MedicalRecordEntity();
                    medicalRecord = medicalRecordRepository.save(medicalRecord);
                    existingAppointment.setMedicalRecordId(medicalRecord.getId());
                }

                // Update medical record details
                medicalRecord.setDiagnosis(appointmentDetailDTO.getDiagnosis());
                medicalRecord.setTreatment(appointmentDetailDTO.getTreatment());

                // Save updated medical record
                medicalRecordRepository.save(medicalRecord);

                // Save updated appointment
                return appointmentRepository.save(existingAppointment);
            } else {
                throw new RuntimeException("Appointment not found with id: " + appointmentDetailDTO.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating appointment: " + e.getMessage(), e);
        }
    }


    private List<AppointmentTimeResponse> generateNext3DaysAppointments() {
        List<AppointmentTimeResponse> listAppointmentAvailable = new ArrayList<>();

        // Example appointment times for each day using the enum
        AppointmentTime[] appointmentTimes = AppointmentTime.values();

        // Generating the next 3 days
        for (int i = 0; i < 3; i++) {
            LocalDate date = LocalDate.now().plusDays(i + 1);

            for (AppointmentTime time : appointmentTimes) {
                AppointmentTimeResponse appointment = new AppointmentTimeResponse() {
                    @Override
                    public LocalDate getAppointmentDate() {
                        return date;
                    }

                    @Override
                    public AppointmentTime getAppointmentTime() {
                        return time;
                    }
                };

                listAppointmentAvailable.add(appointment);
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
            String patientName = userRepository.findById(appointment.getPatientId()).map(UserEntity::getFullName).orElse("");
            String doctorName = userRepository.findById(appointment.getDoctorId()).map(UserEntity::getFullName).orElse("");

            String diagnosis = "";
            String treatment = "";

            if (appointment.getMedicalRecordId() != null) {
                Optional<MedicalRecordEntity> medicalRecordOpt = medicalRecordRepository.findById(appointment.getMedicalRecordId());
                if (medicalRecordOpt.isPresent()) {
                    diagnosis = medicalRecordOpt.get().getDiagnosis();
                    treatment = medicalRecordOpt.get().getTreatment();
                }
            }

            AppointmentDetailDTO dto = new AppointmentDetailDTO(appointment.getId(), patientName, doctorName, appointment.getAppointmentDate(), appointment.getAppointmentTime().name(), appointment.getAppointmentStatus().name(), diagnosis, treatment);

            return Optional.of(dto);
        } else {
            return Optional.empty();
        }
    }

    private AppointmentDTO convertToDTO(AppointmentEntity appointmentEntity) {
        // get patient name by id
        String patientName = userRepository.findById(appointmentEntity.getPatientId()).map(UserEntity::getFullName).orElse("");

        // get doctor name by id
        String doctorName = userRepository.findById(appointmentEntity.getDoctorId()).map(UserEntity::getFullName).orElse("");

        // convert AppointmentEntity to AppointmentDTO
        return new AppointmentDTO(appointmentEntity.getId(), patientName, doctorName, appointmentEntity.getMedicalRecordId(), appointmentEntity.getAppointmentDate(), appointmentEntity.getAppointmentTime().name(), appointmentEntity.getAppointmentStatus().name());
    }

    @Override
    public ResultPaginationDTO handleGetAllAppointments(String patientName, LocalDate date, AppointmentTime time, Pageable pageable) {
        Specification<AppointmentEntity> spec = Specification.where(null);

        // Filter by name
        if (patientName != null && !patientName.isEmpty()) {
            Specification<UserEntity> userSpec = SpecificationUtil.likeIgnoreCase("fullName", patientName);
            List<Integer> patientIds = userRepository.findAll(userSpec).stream().map(UserEntity::getId).collect(Collectors.toList());

            if (!patientIds.isEmpty()) {
                spec = spec.and(SpecificationUtil.in("patientId", patientIds));
            } else {
                // Trả về trang rỗng nếu không có kết quả
                return buildResultPaginationDTO(new PageImpl<>(Collections.emptyList(), pageable, 0));
            }
        }

        // Filter by date
        if (date != null) {
            spec = spec.and(SpecificationUtil.equal("appointmentDate", date));
        }

        // Filter by time
        if (time != null) {
            spec = spec.and(SpecificationUtil.equal("appointmentTime", time));
        }

        Page<AppointmentEntity> pageAppointment = this.appointmentRepository.findAll(spec, pageable);
        Page<AppointmentDTO> pageAppointmentDTO = pageAppointment.map(this::convertToDTO);

        return buildResultPaginationDTO(pageAppointment, pageAppointmentDTO);
    }

    private ResultPaginationDTO buildResultPaginationDTO(Page<AppointmentEntity> pageAppointment, Page<AppointmentDTO> pageAppointmentDTO) {
        Meta mt = new Meta();
        mt.setPage(pageAppointment.getNumber() + 1);
        mt.setPageSize(pageAppointment.getSize());
        mt.setPages(pageAppointment.getTotalPages());
        mt.setTotal(pageAppointment.getTotalElements());

        ResultPaginationDTO rs = new ResultPaginationDTO();
        rs.setMeta(mt);
        rs.setResult(pageAppointmentDTO.getContent());

        return rs;
    }

    private ResultPaginationDTO buildResultPaginationDTO(Page<AppointmentEntity> pageAppointment) {
        return buildResultPaginationDTO(pageAppointment, pageAppointment.map(this::convertToDTO));
    }
}
