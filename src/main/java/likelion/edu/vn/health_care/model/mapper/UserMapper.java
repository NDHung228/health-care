package likelion.edu.vn.health_care.model.mapper;

import likelion.edu.vn.health_care.entity.UserEntity;
import likelion.edu.vn.health_care.model.request.UserRequest;
import likelion.edu.vn.health_care.model.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract UserEntity toUserEntity(UserRequest user);


    public abstract UserResponse toUserResponse(UserEntity user);
}
