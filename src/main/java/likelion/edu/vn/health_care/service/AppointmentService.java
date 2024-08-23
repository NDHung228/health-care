package likelion.edu.vn.health_care.service;

import likelion.edu.vn.health_care.entity.AppointmentEntity;
import likelion.edu.vn.health_care.model.dto.AppointmentDetailDTO;
import likelion.edu.vn.health_care.model.request.AppointmentRequest;
import likelion.edu.vn.health_care.model.response.AppointmentTimeResponse;
import java.util.List;
import java.util.Optional;

public interface AppointmentService extends BaseService<AppointmentEntity> {
    AppointmentEntity create(AppointmentRequest appointmentRequest);

    Optional<AppointmentDetailDTO> findAppointmentDetailById(Integer id);

    List<AppointmentTimeResponse> getAppointmentTimeAvailable();
}
