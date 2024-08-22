package likelion.edu.vn.health_care.service.impl;

import likelion.edu.vn.health_care.entity.UserEntity;
import likelion.edu.vn.health_care.exception.ResourceNotFoundException;
import likelion.edu.vn.health_care.model.dto.Meta;
import likelion.edu.vn.health_care.model.dto.ResultPaginationDTO;
import likelion.edu.vn.health_care.model.mapper.UserMapper;
import likelion.edu.vn.health_care.model.response.UserResponse;
import likelion.edu.vn.health_care.repository.UserRepository;
import likelion.edu.vn.health_care.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public void delete(int id) {

    }

    @Override
    public Iterable<UserEntity> findAll() {
        return null;
    }

    @Override
    public Optional<UserEntity> findById(Integer id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not find user by id", "id", id));
        return Optional.ofNullable(user);
    }

    @Override
    public ResultPaginationDTO handleGetAll(Pageable pageable) {
        Page<UserEntity> pageUser = this.userRepository.findAll(pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();

        mt.setPage(pageUser.getNumber() + 1);
        mt.setPageSize(pageUser.getSize());

        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageUser.getContent());

        return rs;
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

    @Override
    public UserResponse getUserById(int id) {
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Not found", "id", id)));
    }


}
