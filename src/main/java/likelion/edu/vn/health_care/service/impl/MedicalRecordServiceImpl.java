package likelion.edu.vn.health_care.service.impl;

import likelion.edu.vn.health_care.entity.MedicalRecordEntity;
import likelion.edu.vn.health_care.repository.MedicalRecordRepository;
import likelion.edu.vn.health_care.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Override
    public MedicalRecordEntity create(MedicalRecordEntity medicalRecordEntity) {
        return medicalRecordRepository.save(medicalRecordEntity);
    }

    @Override
    public MedicalRecordEntity update(MedicalRecordEntity medicalRecordEntity) {
        Optional<MedicalRecordEntity> existingRecord = medicalRecordRepository.findById(medicalRecordEntity.getId());
        if (existingRecord.isPresent()) {
            return medicalRecordRepository.save(medicalRecordEntity);
        } else {
            throw new RuntimeException("Medical record not found with id: " + medicalRecordEntity.getId());
        }
    }

    @Override
    public void delete(MedicalRecordEntity medicalRecordEntity) {
        medicalRecordRepository.delete(medicalRecordEntity);
    }

    @Override
    public Iterable<MedicalRecordEntity> findAll() {
        return medicalRecordRepository.findAll();
    }

    @Override
    public Optional<MedicalRecordEntity> findById(Integer id) {
        return medicalRecordRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<MedicalRecordEntity> existingRecord = medicalRecordRepository.findById(id);
        if (existingRecord.isPresent()) {
            // Xóa hồ sơ bệnh án
            medicalRecordRepository.deleteById(id);
        } else {
            throw new RuntimeException("Medical record not found with id: " + id);
        }
    }
}
