package likelion.edu.vn.health_care.repository;

import likelion.edu.vn.health_care.entity.AppointmentEntity;
import likelion.edu.vn.health_care.entity.UserEntity;
import likelion.edu.vn.health_care.enumration.AppointmentTime;
import likelion.edu.vn.health_care.model.response.AppointmentTimeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Integer>, JpaSpecificationExecutor<AppointmentEntity> {

    @Query(value = "SELECT u.id " +
            "FROM users u " +
            "WHERE u.id NOT IN ( " +
            "    SELECT a.doctor_id " +
            "    FROM appointments a " +
            "    WHERE a.appointment_date::text = ?1 " +
            "    AND a.appointment_time = ?2 and a.appointment_status != 'Cancel' " +
            ") " +
            "AND u.role_id = 2 " +
            "LIMIT 1;", nativeQuery = true)
    Optional<Integer> findAvailableDoctorId(String appointmentDate, String appointmentTime);

    @Query(value = "SELECT DISTINCT " +
            "    a.appointment_date,  " +
            "    a.appointment_time " +
            "FROM  " +
            "    users u " +
            "LEFT JOIN  " +
            "    appointments a ON u.id = a.doctor_id  " +
            "    AND a.appointment_status != 'Cancel' " +
            "WHERE  " +
            "    u.role_id = 2 " +
            "    AND a.appointment_date IS NOT NULL " +
            "    AND a.appointment_time IS NOT NULL " +
            "    AND a.appointment_date BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '3 days' ", nativeQuery = true)
    Optional<List<AppointmentTimeResponse>> listUnavailableAppointments();

    Page<AppointmentEntity> findByPatientId(Integer patientId, Pageable pageable);


}
