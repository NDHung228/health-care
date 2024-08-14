package likelion.edu.vn.health_care.security;


import likelion.edu.vn.health_care.entity.UserEntity;
import likelion.edu.vn.health_care.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserInfoService.class);
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userDetail = repository.findByEmail(email);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found "));
    }

    public UserEntity addUser(UserEntity userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        return repository.save(userInfo);

    }

    public UserEntity authenticateUser(String email, String password) {
        Optional<UserEntity> user = repository.findByEmail(email);
        if (user != null && encoder.matches(password, user.get().getPassword())) {
            return user.get();
        }
        return null;
    }


}
