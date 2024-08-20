package likelion.edu.vn.health_care.repository;

import likelion.edu.vn.health_care.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Integer> {
}
