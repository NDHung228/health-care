package likelion.edu.vn.health_care.service;

import likelion.edu.vn.health_care.entity.MedicalRecordEntity;

public interface MedicalRecordService extends BaseService<MedicalRecordEntity>{
    void deleteById(Integer id);
}
