package likelion.edu.vn.health_care.security;


import likelion.edu.vn.health_care.entity.UserEntity;
import likelion.edu.vn.health_care.model.mapper.UserMapper;
import likelion.edu.vn.health_care.model.request.UserRequest;
import likelion.edu.vn.health_care.model.response.UserResponse;
import likelion.edu.vn.health_care.repository.UserRepository;
import likelion.edu.vn.health_care.security.jwt.JwtUtil;
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

    @Autowired
    private JwtUtil jwtUtil;

    // Mapper
    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userDetail = repository.findByEmail(email);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found "));
    }

    public String addUser(UserRequest userRequest) throws Exception {
        UserEntity userInfo = userMapper.toUserEntity(userRequest);
        userInfo.setPassword(encoder.encode(userRequest.getPassword()));
        userInfo.setRoleId(1);
        try {
            repository.save(userInfo);
            return "register patient success";
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String addDoctor(UserRequest userRequest) throws Exception {
        UserEntity userInfo = userMapper.toUserEntity(userRequest);
        userInfo.setPassword(encoder.encode(userRequest.getPassword()));
        userInfo.setRoleId(2);
        try {
            repository.save(userInfo);
            return "register doctor success";
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String authenticateUser(String email, String password) throws Exception {
        try {
            Optional<UserEntity> user = repository.findByEmail(email);
            if (user != null && encoder.matches(password, user.get().getPassword())) {
                return jwtUtil.generateToken(user.get().getEmail());
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;

    }

    // load user by token instead of data transmission
    public UserResponse getUserDetails() {
        String email = SecurityUtils.getEmail();
        UserResponse userResponse;

        Optional<UserEntity> user = repository.findByEmail(email);
        if (user.isPresent()) {
            userResponse = userMapper.toUserResponse(user.get());
            return userResponse;
        }
        return null;
    }


    public UserEntity getUserEntity() {
        String email = SecurityUtils.getEmail();

        Optional<UserEntity> user = repository.findByEmail(email);
        return user.orElse(null);
    }

    public int getUserId() {
        return getUserEntity().getId();
    }

    public UserResponse updateUser(UserRequest userRequest) throws Exception {
        try {
            UserResponse checkCurrentUser = getUserDetails();

            if (checkCurrentUser != null && checkCurrentUser.getEmail().equals(userRequest.getEmail())) {
                UserEntity userInfo = userMapper.toUserEntity(userRequest);
                userInfo.setId(checkCurrentUser.getId());

                System.err.println("updateUser: " + userInfo);

                userInfo = repository.save(userInfo);
                return userMapper.toUserResponse(userInfo);
            }

            return null;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String deleteUser(int id) throws Exception {
        try {
            repository.deleteById(id);
            return "Delete user success";

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

   public UserEntity saveUser(UserEntity userEntity) throws Exception {
        return repository.save(userEntity);
   }
}
