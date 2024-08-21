package likelion.edu.vn.health_care.service.impl;

import likelion.edu.vn.health_care.entity.UserEntity;
import likelion.edu.vn.health_care.model.dto.ResultPaginationDTO;
import likelion.edu.vn.health_care.model.mapper.UserMapper;
import likelion.edu.vn.health_care.model.response.UserResponse;
import likelion.edu.vn.health_care.repository.UserRepository;
import likelion.edu.vn.health_care.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserEntity create(UserEntity userEntity) {
        return null;
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        return null;
    }

    @Override
    public void delete(UserEntity userEntity) {

    }

    @Override
    public Iterable<UserEntity> findAll() {
        return null;
    }

    @Override
    public Optional<UserEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public ResultPaginationDTO handleGetAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<UserEntity> listUser = userRepository.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();
        for (UserEntity userEntity : listUser) {
            userResponseList.add(userMapper.toUserResponse(userEntity));
        }
        return userResponseList;
    }

    @Override
    public List<UserResponse> getAllDoctor() {
        Optional<List<UserEntity>> listUser = userRepository.findAllByRoleId(2);

        List<UserResponse> userResponseList = new ArrayList<>();
        if (listUser.isPresent()) {
            for (UserEntity userEntity : listUser.get()) {
                userResponseList.add(userMapper.toUserResponse(userEntity));
            }
            return userResponseList;

        }
        return null;
    }

    @Override
    public List<UserResponse> getAllPatient() {
        Optional<List<UserEntity>> listUser = userRepository.findAllByRoleId(1);

        List<UserResponse> userResponseList = new ArrayList<>();
        if (listUser.isPresent()) {
            for (UserEntity userEntity : listUser.get()) {
                userResponseList.add(userMapper.toUserResponse(userEntity));
            }
            return userResponseList;

        }
        return null;
    }

    @Override
    public List<UserResponse> searchName(String name) {
        System.err.println("searchName: " + name);
        Optional<List<UserEntity>> listUserEntity = userRepository.searchByName(name);

        if (listUserEntity.isEmpty()) {
            return null;
        }
        List<UserResponse> userResponseList = new ArrayList<>();

        for (UserEntity userEntity : listUserEntity.get()) {
            userResponseList.add(userMapper.toUserResponse(userEntity));
        }

        return userResponseList;

    }


}
