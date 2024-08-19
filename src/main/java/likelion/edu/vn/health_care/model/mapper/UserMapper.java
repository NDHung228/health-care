package likelion.edu.vn.health_care.model.mapper;

import likelion.edu.vn.health_care.entity.UserEntity;
import likelion.edu.vn.health_care.model.request.UserRequest;
import likelion.edu.vn.health_care.model.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public UserEntity toUserEntity(UserRequest user){
        UserEntity userEntity = new UserEntity();
        userEntity.setDob(user.getDob());
        userEntity.setEmail(user.getEmail());
        userEntity.setFullName(user.getFullName());
        userEntity.setAddress(user.getAddress());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());
        return userEntity;
    }

    public UserResponse toUserResponse(UserEntity user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setDob(user.getDob());
        userResponse.setEmail(user.getEmail());
        userResponse.setFullName(user.getFullName());
        userResponse.setAddress(user.getAddress());
        userResponse.setPhone(user.getPhone());
        userResponse.setId(user.getId());
        userResponse.setGender(user.getGender());
        userResponse.setRoleId(user.getRoleId());

        return userResponse;
    }

    public UserEntity fromUserResponse(UserResponse user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setDob(user.getDob());
        userEntity.setEmail(user.getEmail());
        userEntity.setFullName(user.getFullName());
        userEntity.setAddress(user.getAddress());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());
        userEntity.setRoleId(user.getRoleId());
//        userEntity.setPassword(userEntity.getPassword());
//        userEntity.setCreateTime();
        return userEntity;
    }
}
