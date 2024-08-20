package likelion.edu.vn.health_care.repository;

import likelion.edu.vn.health_care.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);

    @Query(value = "SELECT r.name " +
            "FROM users u " +
            "JOIN roles r ON u.role_id = r.id " +
            "WHERE u.email LIKE '?1' " +
            "LIMIT 1  ",nativeQuery = true)
    Optional<String> findByRoleName(String email);

    Optional<List<UserEntity>> findAllByRoleId(int id);
}
