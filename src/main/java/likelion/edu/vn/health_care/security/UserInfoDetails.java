package likelion.edu.vn.health_care.security;

import likelion.edu.vn.health_care.entity.UserEntity;
import likelion.edu.vn.health_care.enumration.Authorities;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserInfoDetails implements UserDetails {

    private final String name;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public UserInfoDetails(UserEntity userInfo) {
        name = userInfo.getEmail();
        password = userInfo.getPassword();

        authorities = new ArrayList<GrantedAuthority>();
        if (userInfo.getRoleId() == 1) {
            authorities.add(new SimpleGrantedAuthority(Authorities.Admin.name()));
        } else if (userInfo.getRoleId() == 2) {
            authorities.add(new SimpleGrantedAuthority(Authorities.Doctor.name()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Authorities.Patient.name()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }



}
