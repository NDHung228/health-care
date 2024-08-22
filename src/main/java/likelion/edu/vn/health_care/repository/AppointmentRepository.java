package likelion.edu.vn.health_care.repository;

import likelion.edu.vn.health_care.entity.AppointmentEntity;
import likelion.edu.vn.health_care.entity.UserEntity;
import likelion.edu.vn.health_care.enumration.AppointmentTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Integer> {


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




}
