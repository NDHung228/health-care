package likelion.edu.vn.health_care.service;

import likelion.edu.vn.health_care.entity.UserEntity;
import likelion.edu.vn.health_care.model.dto.ResultPaginationDTO;
import likelion.edu.vn.health_care.model.response.UserResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface UserService extends BaseService<UserEntity> {

    List<UserResponse> getAllUser();
    List<UserResponse> getAllDoctor();
    List<UserResponse> getAllPatient();
    List<UserResponse> searchName(String name);
    UserResponse getUserById(int id);

    ResultPaginationDTO handlegetAllUsers(Specification<UserEntity> spec, Pageable pageable);
    ResultPaginationDTO handleGetAllUsers(String name, String email, Integer roleId, Pageable pageable);
}
