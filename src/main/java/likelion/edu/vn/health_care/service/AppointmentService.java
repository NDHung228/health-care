package likelion.edu.vn.health_care.service;


import likelion.edu.vn.health_care.entity.AppointmentEntity;

public interface AppointmentService extends BaseService<AppointmentEntity>{
    void deleteById(Integer id);
}
