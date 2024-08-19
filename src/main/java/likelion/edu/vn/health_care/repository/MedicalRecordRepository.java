package likelion.edu.vn.health_care.repository;

import likelion.edu.vn.health_care.entity.MedicalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecordEntity, Integer> {
}
