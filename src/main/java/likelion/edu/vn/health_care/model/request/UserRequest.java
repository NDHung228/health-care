package likelion.edu.vn.health_care.model.request;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {
    private String email;
    private String password;
    private String phone;
    private String gender;
    private String address;
    private Date dob;
    private String fullName;
}
