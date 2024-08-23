package likelion.edu.vn.health_care.model.dto;

import likelion.edu.vn.health_care.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private Date dob;
    private String avatarUrl;
    private int roleId;

    public static UserDTO convertToDTO(UserEntity userEntity) {
        return new UserDTO(
                userEntity.getId(),
                userEntity.getFullName(),
                userEntity.getEmail(),
                userEntity.getPhone(),
                userEntity.getAddress(),
                userEntity.getGender(),
                userEntity.getDob(),
                userEntity.getAvatarUrl(),
                userEntity.getRoleId()
        );
    }
}
