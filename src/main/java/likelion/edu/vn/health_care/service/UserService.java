package likelion.edu.vn.health_care.service;

import likelion.edu.vn.health_care.entity.UserEntity;
import likelion.edu.vn.health_care.model.response.UserResponse;

import java.util.List;

public interface UserService extends BaseService<UserEntity> {

    List<UserResponse> getAllUser();
    List<UserResponse> getAllDoctor();
    List<UserResponse> getAllPatient();

}
