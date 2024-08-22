package likelion.edu.vn.health_care.service;


import likelion.edu.vn.health_care.entity.AppointmentEntity;
import likelion.edu.vn.health_care.model.request.AppointmentRequest;

public interface AppointmentService extends BaseService<AppointmentEntity>{
    AppointmentEntity create(AppointmentRequest appointmentRequest);
}
