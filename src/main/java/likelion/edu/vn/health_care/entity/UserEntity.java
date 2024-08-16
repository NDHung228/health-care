package likelion.edu.vn.health_care.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity extends BaseEntity{

    @NonNull
    @Column(unique = true, nullable = false)
    private String email;

    @NonNull
    private String password;

    @NonNull
    private int roleId;

    @NonNull
    private String fullName;

    @NonNull
    private Date dob;

    public @NonNull String getFullName() {
        return fullName;
    }

    public void setFullName(@NonNull String fullName) {
        this.fullName = fullName;
    }

    @NonNull
    private String phone;

    @NonNull
    private String address;

    @NonNull
    private String gender;

    @NonNull
    private String avatarUrl;

    public @NonNull String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public @NonNull String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(@NonNull int roleId) {
        this.roleId = roleId;
    }

    public @NonNull Date getDob() {
        return dob;
    }

    public void setDob(@NonNull Date dob) {
        this.dob = dob;
    }

    public @NonNull String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    public @NonNull String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    public @NonNull String getGender() {
        return gender;
    }

    public void setGender(@NonNull String gender) {
        this.gender = gender;
    }

    public @NonNull String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(@NonNull String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}

