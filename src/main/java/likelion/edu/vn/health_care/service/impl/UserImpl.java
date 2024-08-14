package likelion.edu.vn.health_care.service.impl;

import likelion.edu.vn.health_care.entity.UserEntity;
import likelion.edu.vn.health_care.repository.UserRepository;
import likelion.edu.vn.health_care.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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
    public Optional<UserEntity> findById(Long id) {
        return Optional.empty();
    }
}
