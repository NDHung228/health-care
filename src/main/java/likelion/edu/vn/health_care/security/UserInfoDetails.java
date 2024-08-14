package likelion.edu.vn.health_care.security;

import likelion.edu.vn.health_care.entity.UserEntity;
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
//        if(userInfo.getRole_id() == 1) {
//            authorities.add(new SimpleGrantedAuthority(Authorities.USER.name()));
//        } else {
//            authorities.add(new SimpleGrantedAuthority(Authorities.ADMIN.name()));
//        }

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
