package likelion.edu.vn.health_care.model.response;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private int id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private LocalDate dob;
    private String avatarUrl;
    private int roleId;

    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", dob=" + dob +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
